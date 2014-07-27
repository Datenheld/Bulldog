package org.bulldog.core.gpio.event;

public interface ThresholdListener {

	void thresholdReached();
	boolean isThresholdReached(double thresholdValue);
	
}
