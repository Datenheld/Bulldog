package org.bulldog.core.gpio;

import org.bulldog.core.Signal;

public interface DigitalOutput extends PinFeature {

	void write(Signal signal);
	void applySignal(Signal signal);
	Signal getAppliedSignal();
	
	void high();
	void low();
	void toggle();
	
	boolean isHigh();
	boolean isLow();
	
	void startBlinking(int periodLengthMilliseconds);
	void startBlinking(int periodLengthMilliseconds, int durationMilliseconds);
	void blinkTimes(int periodLengthMilliseconds, int times);
	void stopBlinking();
}