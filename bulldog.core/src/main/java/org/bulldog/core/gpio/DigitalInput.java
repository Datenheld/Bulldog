package org.bulldog.core.gpio;

import org.bulldog.core.Edge;
import org.bulldog.core.Signal;
import org.bulldog.core.gpio.event.InterruptEventArgs;
import org.bulldog.core.gpio.event.InterruptListener;

public interface DigitalInput extends PinFeature {

	void disableInterrupts();
	void enableInterrupts();
	
	boolean areInterruptsEnabled();
	
	Signal readSignal();
	Signal readSignalDebounced(int debounceMilliseconds);
	
	void setInterruptTrigger(Edge edge);
	Edge getInterruptTrigger();
	
	void setInterruptDebounceTime(int milliSeconds);
	int getInterruptDebounceTime();
	
	void fireInterruptEvent(InterruptEventArgs args);
	void addInterruptListener(InterruptListener listener);
	void removeInterruptListener(InterruptListener listener);
	void clearInterruptListener();
	
}