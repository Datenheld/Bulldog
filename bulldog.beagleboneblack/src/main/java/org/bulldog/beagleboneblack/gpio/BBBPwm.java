package org.bulldog.beagleboneblack.gpio;

import java.io.File;

import org.bulldog.beagleboneblack.BeagleBonePin;
import org.bulldog.beagleboneblack.sysfs.SysFs;
import org.bulldog.beagleboneblack.sysfs.SysFsPwm;
import org.bulldog.core.Polarity;
import org.bulldog.core.gpio.Pin;
import org.bulldog.core.gpio.base.AbstractPwm;

public class BBBPwm extends AbstractPwm {
	
	private static final String PWM_FILENAME_FORMAT = "bone_pwm_%s_%d";
	private static final String PWM_OCP_PATTERN = "pwm_test_%s_%d";
	private static final long NANOSECONDS_PER_SECOND = 1000000000;
	
	private static BBBPwmManager PWM_MANAGER = new BBBPwmManager();
	private static Object LOCK_OBJECT = new Object();
	
	private SysFs sysfsWrapper = new SysFs();
	private SysFsPwm sysFsPwm = null;
	private String pwmGroup = "";
	private String qualifier = "";;
	
	public BBBPwm(Pin pin, String pwmGroup, String qualifier) {
		super(pin);
		this.pwmGroup = pwmGroup;
		this.qualifier = qualifier;
	}

	public void setup() {
		synchronized(LOCK_OBJECT) {
			if(!PWM_MANAGER.canActivatePwmOnPin(this)) {
				BBBPwm activePwm  = PWM_MANAGER.getActivePin(this);
				throw new RuntimeException("You cannot activate "+activePwm.getName()+" on this pin. It is already active on: " + activePwm.getPin().getName());
			}
			
			BeagleBonePin bbbPin = (BeagleBonePin)getPin();
			sysfsWrapper.createSlotIfNotExists("am33xx_pwm");
			sysfsWrapper.createSlotIfNotExists(String.format(PWM_FILENAME_FORMAT, bbbPin.getPort(), bbbPin.getIndexOnPort()));
			
			String deviceName = String.format(PWM_OCP_PATTERN, bbbPin.getPort(), bbbPin.getIndexOnPort());
		 	File file = sysfsWrapper.findOcpDevice(deviceName);
			sysFsPwm = new SysFsPwm(file.getAbsolutePath(), sysfsWrapper.getSlotNumber(deviceName));
			sysFsPwm.setPolarity(Polarity.Negative);   //period to negative - that means the pulse will be high
		}
	}

	public void teardown() {
		synchronized(LOCK_OBJECT) {
			BeagleBonePin bbbPin = (BeagleBonePin)getPin();
			String deviceName = String.format(PWM_FILENAME_FORMAT, bbbPin.getPort(), bbbPin.getIndexOnPort());
			sysfsWrapper.removeSlotOfDevice(deviceName);
		}
	}

	@Override
	protected void setPwmImpl(float frequency, float duty) {
		synchronized(LOCK_OBJECT) {
			long period = (long) ((1.0 / frequency) * (float)NANOSECONDS_PER_SECOND);
			long dutyCycle = (long) (period * duty);
			
			// The Beaglebone can only have the same frequency on all
			// pwm groups. That means we need to tear down sibling pwms
			// in order to activate the new frequecy for the group
			BBBPwm siblingPwm = PWM_MANAGER.getActiveSibling(this);
			if(siblingPwm != null) {
				siblingPwm.teardown();
			}
			
			sysFsPwm.setDuty(dutyCycle);
			sysFsPwm.setPeriod(period);
			
			// setup the sibling again -it shares the same frequency
			siblingPwm.setup();
		}
	}
	
	@Override
	protected void enableImpl() {
		sysFsPwm.enable();
	}

	@Override
	protected void disasbleImpl() {
		sysFsPwm.disable();
	}
	
	public String getPwmGroup() {
		return this.pwmGroup;
	}
	
	public String getQualifier() {
		return this.qualifier;
	}

	public String getName() {
		return getPwmGroup() + getQualifier();
	}
}
