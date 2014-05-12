package org.bulldog.core.gpio;

public interface Pwm extends PinFeature {

	void enable();
	void disable();
	boolean isEnabled();
	
	void setDuty(float duty);
	float getDuty();
	
	void setFrequency(long frequency);
	long getFrequency();
}