package org.bulldog.core.gpio;

public interface Pwm extends PinFeature {

	void enable();
	void disable();
	boolean isEnabled();
	
	void setDuty(float duty);
	float getDuty();
	
	void setFrequency(float frequency);
	float getFrequency();
}