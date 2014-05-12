package org.bulldog.beagleboneblack.gpio;

import java.io.File;

import org.bulldog.beagleboneblack.sysfs.SysFs;
import org.bulldog.beagleboneblack.sysfs.SysFsPwm;
import org.bulldog.bulldog.beagleboneblack.BeagleBonePin;
import org.bulldog.core.Polarity;
import org.bulldog.core.gpio.Pin;
import org.bulldog.core.gpio.base.AbstractPwm;

public class BBBPwm extends AbstractPwm {

	private static final String PWM_FILENAME_FORMAT = "bone_pwm_P%d_%d";
	private static final String PWM_OCP_PATTERN = "pwm_test_P%d_%d";
	private static final long NANOSECONDS_PER_SECOND = 1000000000;
	
	private SysFs sysfsWrapper = new SysFs();
	private SysFsPwm sysFsPwm = null;
	
	public BBBPwm(Pin pin) {
		super(pin);
	}

	public void setup() {
		BeagleBonePin bbbPin = (BeagleBonePin)getPin();
		sysfsWrapper.createSlotIfNotExists("am33xx_pwm");
		sysfsWrapper.createSlotIfNotExists(String.format(PWM_FILENAME_FORMAT, bbbPin.getPort(), bbbPin.getIndexOnPort()));
		
		String deviceName = String.format(PWM_OCP_PATTERN, bbbPin.getPort(), bbbPin.getIndexOnPort());
	 	File file = sysfsWrapper.findOcpDevice(deviceName);
		sysFsPwm = new SysFsPwm(file.getAbsolutePath(), sysfsWrapper.getSlotNumber(deviceName));
	}

	public void teardown() {
		BeagleBonePin bbbPin = (BeagleBonePin)getPin();
		String deviceName = String.format(PWM_FILENAME_FORMAT, bbbPin.getPort(), bbbPin.getIndexOnPort());
		sysfsWrapper.removeSlotOfDevice(deviceName);
	}

	@Override
	protected void setPwmImpl(long frequency, float duty) {
		long period = (long) ((1.0 / frequency) * (float)NANOSECONDS_PER_SECOND);
		long dutyCycle = (long) (period * duty);
		sysFsPwm.setPolarity(Polarity.Negative);   //period to negative - that means the pulse will be high
		sysFsPwm.setDuty(dutyCycle);
		sysFsPwm.setPeriod(period);
	}

	@Override
	protected void enableImpl() {
		sysFsPwm.enable();
	}

	@Override
	protected void disasbleImpl() {
		sysFsPwm.disable();
	}

}
