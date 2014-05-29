package org.bulldog.core.gpio.base;

import org.bulldog.core.gpio.Pin;
import org.bulldog.core.gpio.Pwm;

public abstract class AbstractPwm extends AbstractPinFeature implements Pwm {

	private String NAME_FORMAT = "PWM - status '%s' - frequency '%d' with duty '%.2f' on Pin %s";

	private float duty = 0.0f;
	private float frequency = 0;
	private boolean enabled = false;
	
	public AbstractPwm(Pin pin) {
		super(pin);
	}

	public String getName() {
		return String.format(NAME_FORMAT, isEnabled() ? "enabled" : "disabled", getFrequency(), getDuty(), getPin().getName());
	}
	
	public void enable() {
		enableImpl();
		enabled = true;
	}
	
	public void disable() {
		disasbleImpl();
		enabled = false;
	}
	
	public boolean isEnabled() {
		return enabled;
	}
	
	public void setDuty(float duty) {
		if(duty < 0.0 && duty > 1.0) {
			throw new IllegalArgumentException("Duty cannot be less than 0.0 or greater 1.0");
		}
		this.duty = duty;
		setPwmImpl(getFrequency(), duty);
	}
	
	public float getDuty() {
		return duty;
	}
	
	public void setFrequency(float frequency) {
		this.frequency = frequency;
		setPwmImpl(frequency, getDuty());
	}
	
	public float getFrequency() {
		return frequency;
	}

	public boolean isBlocking() {
		return false;
	}
	
	protected abstract void setPwmImpl(float frequency, float duty);
	protected abstract void enableImpl();
	protected abstract void disasbleImpl();

}
