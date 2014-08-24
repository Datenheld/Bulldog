package org.bulldog.devices.switches;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bulldog.core.Edge;
import org.bulldog.core.Signal;
import org.bulldog.core.gpio.DigitalInput;
import org.bulldog.core.gpio.event.InterruptEventArgs;
import org.bulldog.core.gpio.event.InterruptListener;

public class Button implements InterruptListener {

	private static final int DEFAULT_DEBOUNCE_TIME_MS = 0;
	
	private DigitalInput input;
	private Signal activeState = Signal.Low;
	private List<ButtonListener> listeners = Collections.synchronizedList(new ArrayList<ButtonListener>());
	
	public Button(DigitalInput input, int debounceMilliseconds, Signal activeState) {
		this.input = input;
		this.input.setInterruptDebounceMs(debounceMilliseconds);
		this.input.addInterruptListener(this);
		this.input.enableInterrupts();
		this.activeState = activeState;
	}
	
	public Button(DigitalInput input, Signal activeState) {
		this(input, DEFAULT_DEBOUNCE_TIME_MS, activeState);
	}
	
	protected void fireButtonReleased() {
		synchronized(listeners) {
			for(ButtonListener listener : listeners) {
				listener.buttonReleased();
			}
		}
	}
	
	protected void fireButtonPressed() {
		synchronized(listeners) {
			for(ButtonListener listener : listeners) {
				listener.buttonPressed();
			}
		}
	}
	
	public void addListener(ButtonListener listener) {
		this.listeners.add(listener);
	}

	public void removeListener(ButtonListener listener) {
		this.listeners.remove(listener);
	}

	public void clearListeners() {
		this.listeners.clear();
	}

	@Override
	public void interruptRequest(InterruptEventArgs args) {
		if(args.getEdge() == Edge.Rising && activeState == Signal.High) {
			fireButtonPressed();
		} else if(args.getEdge() == Edge.Falling && activeState == Signal.Low) {
			fireButtonPressed();
		} else {
			fireButtonReleased();
		}
	}
	
	public boolean isButtonPressed() {
		return input.read() == activeState;
	}
	
}
