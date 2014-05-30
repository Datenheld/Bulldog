package org.bulldog.core.gpio.base;

import org.bulldog.core.gpio.Pin;
import org.bulldog.core.gpio.PinFeature;

public abstract class AbstractPinFeature implements PinFeature {

	private Pin pin;
	private boolean blocking = false;
	
	public AbstractPinFeature(Pin pin) {
		this.pin = pin;
	}

	public Pin getPin() {
		return pin;
	}
	
	public boolean isActivatedFeature() {
		return this.getPin().getActiveFeature() == this;
	}
	
	public void activate() {
		getPin().activateFeature(getClass());
	}
	
	public void setBlocking(boolean blocking) {
		this.blocking = blocking;
	}
	
	public boolean isBlocking() {
		return blocking;
	}
}
