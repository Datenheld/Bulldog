package org.bulldog.core.gpio.util;

import org.bulldog.core.gpio.Pin;
import org.bulldog.core.gpio.Pwm;
import org.bulldog.core.gpio.base.AbstractPinFeature;

public class SoftPwm extends AbstractPinFeature implements Pwm {

	public SoftPwm(Pin pin) {
		super(pin);
	}

	@Override
	public String getName() {
		return null;
	}

	@Override
	public void setup() {
	}

	@Override
	public void teardown() {
	}

	@Override
	public void enable() {
	}

	@Override
	public void disable() {
	}

	@Override
	public boolean isEnabled() {
		return false;
	}

	@Override
	public void setDuty(float duty) {
	}

	@Override
	public float getDuty() {
		return 0;
	}

	@Override
	public void setFrequency(float frequency) {
	}

	@Override
	public float getFrequency() {
		return 0;
	}

}
