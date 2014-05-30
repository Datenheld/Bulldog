package org.bulldog.beagleboneblack.gpio;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import org.bulldog.beagleboneblack.sysfs.SysFs;
import org.bulldog.beagleboneblack.sysfs.SysFsPwm;
import org.bulldog.core.gpio.Pin;
import org.bulldog.core.gpio.base.AbstractPwm;
import org.bulldog.core.util.BulldogUtil;

public class BBBPwm extends AbstractPwm {

	private static final String FIRMWARE_PATH = "/lib/firmware/";
	private static final String FILENAME_TEMPLATE = "bd_pwm_%s-%s";
	private static final String PWM_OCP_PATTERN = "pwm_test_%s";
	private static final String VERSION = "00A0";
	
	private static final long NANOSECONDS_PER_SECOND = 1000000000;
	private static BBBPwmManager PWM_MANAGER = new BBBPwmManager();
	
	private int registerAddress;
	private int muxMode;
	private String pwmName;
	private String qualifier;
	private int channel;
	
	private SysFs sysFsWrapper = new SysFs();
	private SysFsPwm sysFsPwm;
	
	private boolean locked = false;
	
	public BBBPwm(Pin pin, int registerAddress, int muxMode, String pwmName, String qualifier, int channel) {
		super(pin);
		this.registerAddress = registerAddress;
		this.muxMode = muxMode;
		this.pwmName = pwmName;
		this.qualifier = qualifier;
		this.channel = channel;
	}

	@Override
	public void setup() {
		if(!PWM_MANAGER.canActivatePwmOnPin(this)) {
			BBBPwm activePwm  = PWM_MANAGER.getActivePin(this);
			throw new RuntimeException("You cannot activate "+activePwm.getName()+" on this pin. It is already active on: " + activePwm.getPin().getName());
		}
	
		sysFsWrapper.createSlotIfNotExists("am33xx_pwm");
		
		BBBPwm siblingPwm = PWM_MANAGER.getActiveSibling(this);
		if(siblingPwm != null) {
			locked = true;
			setFrequency(siblingPwm.getFrequency());
			locked = false;
		}
	}
	
	public void configureOverlay(long period, long duty) {
		try {
			String overlay = createOverlay(period, duty);
			installOverlay(overlay);
			
			sysFsWrapper.createSlotIfNotExists(String.format("bd_pwm_%s", getPin().getName()));
			String deviceName = String.format(PWM_OCP_PATTERN, getPin().getName());
			File sysFsFile = sysFsWrapper.findOcpDevice(deviceName);
			sysFsPwm = new SysFsPwm(sysFsFile.getAbsolutePath(), sysFsWrapper.getSlotNumber(deviceName));
			sysFsPwm.setDuty(duty);
			
			PWM_MANAGER.addActivePwm(this);
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}

	private void installOverlay(String overlay) throws FileNotFoundException, IOException, InterruptedException {
		String objectFile = FIRMWARE_PATH + String.format(FILENAME_TEMPLATE, getPin().getName(), VERSION) + ".dtbo";
		String overlayFile = FIRMWARE_PATH + String.format(FILENAME_TEMPLATE, getPin().getName(), VERSION) + ".dts";
		File file = new File(overlayFile);
		FileOutputStream outputStream = new FileOutputStream(file);
		PrintWriter writer = new PrintWriter(outputStream);
		writer.write(overlay);
		writer.close();
		Process compile = Runtime.getRuntime().exec(String.format("dtc -O dtb -o %s -b 0 -@ %s", objectFile, overlayFile));
		int code = compile.waitFor();
		if(code > 0) {
			throw new RuntimeException("Device Tree Overlay compilation failed: " + overlayFile + " could not be compiled");
		}
	}

	private String createOverlay(long period, long duty) throws IOException {
		InputStream stream = this.getClass().getResourceAsStream("/org/bulldog/beagleboneblack/devicetree/resources/pwm.dts.template");
		String overlay = BulldogUtil.convertStreamToString(stream);
		stream.close();
		
		overlay = overlay.replaceAll("\\$\\{version\\}", VERSION);
		overlay = overlay.replaceAll("\\$\\{pin_name\\}", getPin().getName());
		overlay = overlay.replaceAll("\\$\\{pin_address\\}", "0x"+Integer.toHexString(registerAddress));
		overlay = overlay.replaceAll("\\$\\{mux_mode\\}", "0x" +Integer.toHexString(muxMode));
		overlay = overlay.replaceAll("\\$\\{pwm_name\\}", pwmName.toLowerCase());
		overlay = overlay.replaceAll("\\$\\{qualifier\\}", qualifier);
		overlay = overlay.replaceAll("\\$\\{polarity\\}", "0");
		overlay = overlay.replaceAll("\\$\\{channel\\}", String.valueOf(channel));
		overlay = overlay.replaceAll("\\$\\{period\\}", String.valueOf(period));
		overlay = overlay.replaceAll("\\$\\{duty\\}", String.valueOf(duty));
		overlay = overlay.replaceAll("\\$\\{pin_name_dotted\\}", getPin().getPort() + "." + getPin().getIndexOnPort());
		return overlay;
	}
	
	@Override
	protected void setPwmImpl(float frequency, float duty) {
		if(locked) { return; }
		locked = true;
		long period = (long) ((1.0 / frequency) * (float)NANOSECONDS_PER_SECOND);
		long dutyCycle = (long) (period * duty);
		
		teardown();
		
		// The Beaglebone can only have the same frequency on all
		// pwm groups. That means we need to tear down sibling pwms
		// in order to activate the new frequecy for the group
		BBBPwm siblingPwm = PWM_MANAGER.getActiveSibling(this);
		if(siblingPwm != null) {
			siblingPwm.teardown();
			siblingPwm.setFrequency(frequency);
			long siblingDuty = (long) (period * siblingPwm.getDuty());
			siblingPwm.configureOverlay(period, siblingDuty);	
		}
		
		configureOverlay(period, dutyCycle);
		locked = false;
	}

	@Override
	public void teardown() {
		sysFsWrapper.removeSlotOfDevice(String.format("bd_pwm_%s", getPin().getName()));
	}


	@Override
	protected void enableImpl() {
		if(sysFsPwm == null) { return; }
		sysFsPwm.enable();
	}

	@Override
	protected void disasbleImpl() {
		if(sysFsPwm == null) { return; }
		sysFsPwm.disable();
	}
	
	public String getPwmGroup() {
		return this.pwmName;
	}
	
	public String getQualifier() {
		return this.qualifier;
	}

}
