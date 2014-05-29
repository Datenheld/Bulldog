package org.bulldog.core.gpio.base;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bulldog.core.Edge;
import org.bulldog.core.gpio.DigitalInput;
import org.bulldog.core.gpio.Pin;
import org.bulldog.core.gpio.event.InterruptEventArgs;
import org.bulldog.core.gpio.event.InterruptListener;

public abstract class AbstractDigitalInput extends AbstractPinFeature implements DigitalInput {

	private static final String NAME_FORMAT = "Interrupt on Pin %s";
	private Edge trigger = Edge.Both;
	private List<InterruptListener> interruptListeners = Collections.synchronizedList(new ArrayList<InterruptListener>());
	private int setDebounceMs = 0;
	
	public AbstractDigitalInput(Pin pin) {
		super(pin);
	}

	public String getName() {
		return String.format(NAME_FORMAT, getPin().getName());
	}
	
	public void setInterruptTrigger(Edge edge) {
		this.trigger = edge;
		setInterruptTriggerImpl(edge);
	}
	
	public void setDebounceMs(int milliSeconds) {
		this.setDebounceMs = milliSeconds;
		setInterruptDebounceTimeImpl(milliSeconds);
	}
	
	public int getDebounceMs() {
		return setDebounceMs;
	}
	
	public Edge getInterruptTrigger() {
		return this.trigger;
	}

	public boolean isBlocking() {
		return false;
	}
	
	public void addInterruptListener(InterruptListener listener) {
		interruptListeners.add(listener);
	}
	
	public void removeInterruptListener(InterruptListener listener) {
		interruptListeners.remove(listener);
	}
	
	public List<InterruptListener> getInterruptListeners() {
		return interruptListeners;
	}
	
	public void clearInterruptListeners() {
		interruptListeners.clear();
	}
	
	public void fireInterruptEvent(InterruptEventArgs args) {
		synchronized(interruptListeners) {
			for(InterruptListener listener : interruptListeners) {
				listener.interruptRequest(args);
			}
		}
	}
	
	protected abstract void setInterruptDebounceTimeImpl(int ms);
	protected abstract void setInterruptTriggerImpl(Edge edge);
}
