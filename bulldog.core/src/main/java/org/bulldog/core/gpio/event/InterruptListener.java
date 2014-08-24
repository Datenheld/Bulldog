package org.bulldog.core.gpio.event;

public interface InterruptListener {

	void interruptRequest(InterruptEventArgs args);
	
}
