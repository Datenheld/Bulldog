package org.bulldog.core.gpio.base;

import org.bulldog.core.Signal;
import org.bulldog.core.gpio.DigitalOutput;
import org.bulldog.core.gpio.Pin;

public abstract class AbstractDigitalOutput extends AbstractPinFeature implements DigitalOutput {

	private static final String NAME_FORMAT = "Digital Output - Signal '%s' on Pin %s";
	
	private Signal signal = Signal.Low;

	public AbstractDigitalOutput(Pin pin) {
		super(pin);
	}
	
	public String getName() {
		return String.format(NAME_FORMAT, signal, getPin().getName());
	}

	public void applySignal(Signal signal) {
		this.signal = signal;
		applySignalImpl(this.signal);
	}

	public void high() {
		applySignal(Signal.High);
	}

	public void low() {
		applySignal(Signal.Low);
	}
	
	public boolean isHigh() {
		return signal == Signal.High;
	}
	
	public boolean isLow() {
		return signal == Signal.Low;
	}

	public void toggle() {
		applySignal(signal == Signal.High ? Signal.Low : Signal.High);
	}

	public Signal getAppliedSignal() {
		return signal;
	}
	
	public boolean isBlocking() {
		return false;
	}
	
	protected abstract void applySignalImpl(Signal signal);
}
