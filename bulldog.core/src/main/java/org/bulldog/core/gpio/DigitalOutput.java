package org.bulldog.core.gpio;

import org.bulldog.core.Signal;

public interface DigitalOutput extends PinFeature {

	void applySignal(Signal signal);
	Signal getAppliedSignal();
	
	void high();
	void low();
	void toggle();
	
	
}