package org.bulldog.core.pinfeatures;

import org.bulldog.core.util.easing.Easing;
import org.bulldog.core.util.easing.EasingOptions;


public interface Pwm extends PinFeature {

	void enable();
	void disable();
	boolean isEnabled();
	
	void setDuty(double duty);
	double getDuty();
	
	void setFrequency(double frequency);
	double getFrequency();
	
	void dutyTransition(double toDuty, int milliseconds, Easing easing, EasingOptions option);
	void frequencyTransition(double toFrequency, int milliseconds, Easing easing, EasingOptions option);
}