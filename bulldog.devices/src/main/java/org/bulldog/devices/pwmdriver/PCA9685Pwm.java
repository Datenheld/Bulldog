package org.bulldog.devices.pwmdriver;

import org.bulldog.core.pinfeatures.Pin;
import org.bulldog.core.pinfeatures.PinFeatureConfiguration;
import org.bulldog.core.pinfeatures.base.AbstractPwm;

public class PCA9685Pwm extends AbstractPwm {

	private PCA9685 driver;
	
	public PCA9685Pwm(Pin pin, PCA9685 driver) {
		super(pin);
		this.driver = driver;
	}

	@Override
	protected void setupImpl(PinFeatureConfiguration configuration) {
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
