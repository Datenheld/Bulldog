package org.bulldog.devices.pwmdriver;

import org.bulldog.core.gpio.Pin;
import org.bulldog.core.gpio.base.AbstractPwm;

public class PCA9685Pwm extends AbstractPwm {

	private PCA9685 driver;
	
	public PCA9685Pwm(Pin pin, PCA9685 driver) {
		super(pin);
		this.driver = driver;
	}

	@Override
	protected void setupImpl() {
	}

	@Override
	protected void teardownImpl() {
}

	@Override
	protected void setPwmImpl(double frequency, double duty) {
		driver.setFrequency(frequency);
		driver.setDuty(getPin().getAddress(), duty);
	}

	@Override
	protected void enableImpl() {
		driver.enableChannel(getPin().getAddress());
	}

	@Override
	protected void disableImpl() {
		driver.enableChannel(getPin().getAddress());
	}

}
