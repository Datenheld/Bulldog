package org.bulldog.core.gpio;

import java.util.List;

import org.bulldog.core.Edge;
import org.bulldog.core.Signal;
import org.bulldog.core.gpio.event.InterruptEventArgs;
import org.bulldog.core.gpio.event.InterruptListener;

public interface DigitalInput extends PinFeature {

	Signal read();
	Signal readDebounced(int debounceMilliseconds);
	
	void disableInterrupts();
	void enableInterrupts();
	boolean areInterruptsEnabled();
	
	void setInterruptDebounceMs(int milliSeconds);
	int getInterruptDebounceMs();
	
	void setInterruptTrigger(Edge edge);
	Edge getInterruptTrigger();
	
	void fireInterruptEvent(InterruptEventArgs args);
	void addInterruptListener(InterruptListener listener);
	void removeInterruptListener(InterruptListener listener);
	void clearInterruptListeners();
	List<InterruptListener> getInterruptListeners();

}