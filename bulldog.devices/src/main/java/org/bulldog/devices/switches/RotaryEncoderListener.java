package org.bulldog.devices.switches;

import org.bulldog.core.gpio.event.ValueChangedListener;


public interface RotaryEncoderListener extends ValueChangedListener<Integer> {

	void turnedClockwise();
	void turnedCounterclockwise();
	
}
