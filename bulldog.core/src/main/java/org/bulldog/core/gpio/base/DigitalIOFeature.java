package org.bulldog.core.gpio.base;

import java.util.List;

import org.bulldog.core.Edge;
import org.bulldog.core.Signal;
import org.bulldog.core.gpio.DigitalIO;
import org.bulldog.core.gpio.DigitalInput;
import org.bulldog.core.gpio.DigitalOutput;
import org.bulldog.core.gpio.Pin;
import org.bulldog.core.gpio.event.InterruptEventArgs;
import org.bulldog.core.gpio.event.InterruptListener;

public class DigitalIOFeature extends AbstractPinFeature implements DigitalIO {

	private static final String NAME_FORMAT = "Digital IO on Pin %s";
	private DigitalOutput output;
	private DigitalInput input;
	
	public DigitalIOFeature(Pin pin, DigitalInput input, DigitalOutput output) {
		super(pin);
		this.output = output;
		this.input = input;
	}

	@Override
	public Signal read() {
		setupInputIfNecessary();
		return input.read();
	}

	private void setupInputIfNecessary() {
		if(!input.isSetup()) {
			if(output.isSetup()) { output.teardown(); }
			input.setup();
		}
	}
	
	private void setupOutputIfNecessary() {
		if(!output.isSetup()) {
			if(input.isSetup()) { input.teardown(); };
			output.setup();
		}
	}

	@Override
	public Signal readDebounced(int debounceMilliseconds) {
		setupInputIfNecessary();
		return input.readDebounced(debounceMilliseconds);
	}

	@Override
	public void disableInterrupts() {
		input.disableInterrupts();
	}

	@Override
	public void enableInterrupts() {
		input.enableInterrupts();
	}

	@Override
	public boolean areInterruptsEnabled() {
		return input.areInterruptsEnabled();
	}

	@Override
	public void setInterruptDebounceMs(int milliSeconds) {
		input.setInterruptDebounceMs(milliSeconds);
	}

	@Override
	public int getInterruptDebounceMs() {
		return input.getInterruptDebounceMs();
	}

	@Override
	public void setInterruptTrigger(Edge edge) {
		input.setInterruptTrigger(edge);
	}

	@Override
	public Edge getInterruptTrigger() {
		return input.getInterruptTrigger();
	}

	@Override
	public void fireInterruptEvent(InterruptEventArgs args) {
		input.fireInterruptEvent(args);
	}

	@Override
	public void addInterruptListener(InterruptListener listener) {
		input.addInterruptListener(listener);
	}

	@Override
	public void removeInterruptListener(InterruptListener listener) {
		input.removeInterruptListener(listener);
	}

	@Override
	public void clearInterruptListeners() {
		input.clearInterruptListeners();
	}

	@Override
	public List<InterruptListener> getInterruptListeners() {
		return input.getInterruptListeners();
	}

	@Override
	public String getName() {
		return String.format(NAME_FORMAT, getPin());
	}

	@Override
	public void write(Signal signal) {
		setupOutputIfNecessary();
		output.write(signal);
	}

	@Override
	public void applySignal(Signal signal) {
		setupOutputIfNecessary();
		output.applySignal(signal);
	}

	@Override
	public Signal getAppliedSignal() {
		return output.getAppliedSignal();
	}

	@Override
	public void high() {
		setupOutputIfNecessary();
		output.high();
	}

	@Override
	public void low() {
		setupOutputIfNecessary();
		output.low();
	}

	@Override
	public void toggle() {
		setupOutputIfNecessary();
		output.toggle();
	}

	@Override
	public boolean isHigh() {
		setupOutputIfNecessary();
		return output.isHigh();
	}

	@Override
	public boolean isLow() {
		setupOutputIfNecessary();
		return output.isLow();
	}

	@Override
	public void startBlinking(int periodLengthMilliseconds) {
		setupOutputIfNecessary();
		output.startBlinking(periodLengthMilliseconds);
	}

	@Override
	public void startBlinking(int periodLengthMilliseconds,
			int durationMilliseconds) {
		setupOutputIfNecessary();
		output.startBlinking(periodLengthMilliseconds, durationMilliseconds);
		
	}

	@Override
	public void blinkTimes(int periodLengthMilliseconds, int times) {
		setupOutputIfNecessary();
		output.blinkTimes(periodLengthMilliseconds, times);
	}

	@Override
	public void stopBlinking() {
		setupOutputIfNecessary();
		output.stopBlinking();
	}
	
	@Override
	public boolean isBlinking() {
		return output.isBlinking();
	}

	@Override
	public void awaitBlinkingStopped() {
		output.awaitBlinkingStopped();
	}
	
	@Override
	protected void setupImpl() {

	}

	@Override
	protected void teardownImpl() {

	}

}
