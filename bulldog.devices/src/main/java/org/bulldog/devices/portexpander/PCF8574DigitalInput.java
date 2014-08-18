package org.bulldog.devices.portexpander;

import org.bulldog.core.Edge;
import org.bulldog.core.Signal;
import org.bulldog.core.gpio.Pin;
import org.bulldog.core.gpio.base.AbstractDigitalInput;
import org.bulldog.core.gpio.event.InterruptEventArgs;
import org.bulldog.core.util.BitMagic;

public class PCF8574DigitalInput extends AbstractDigitalInput {

	private PCF8574 expander;
	
	public PCF8574DigitalInput(Pin pin, PCF8574 expander) {
		super(pin);
		this.expander = expander;
	}

	@Override
	public Signal read() {
		byte state = expander.readState();
		return Signal.fromNumericValue((state >> getPin().getAddress()) & 1);
	}

	@Override
	protected void setupImpl() {
		byte state = expander.getState();
		byte newState = BitMagic.setBit(state, getPin().getAddress(), 1);
		expander.writeState(newState);
	}

	@Override
	protected void teardownImpl() {
	}

	public void handleInterruptEvent(Signal oldState, Signal currentState) {
		if(!areInterruptsEnabled()) { return; }
		
		Edge edge = determineInterruptEdge(oldState, currentState);
		if(!isInterruptTrigger(edge)) { return; }
		
		fireInterruptEvent(new InterruptEventArgs(getPin(), edge));
	}
		
	private boolean isInterruptTrigger(Edge edge) {
		return edge == getInterruptTrigger() || getInterruptTrigger() == Edge.Both;
	}

	private Edge determineInterruptEdge(Signal oldState, Signal currentState) {
		if(currentState == Signal.Low && oldState == Signal.High) { return Edge.Falling; }
		return Edge.Rising;
	}

	@Override
	protected void enableInterruptsImpl() {}

	@Override
	protected void disableInterruptsImpl() {}
}
