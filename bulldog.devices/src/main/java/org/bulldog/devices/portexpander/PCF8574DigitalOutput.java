package org.bulldog.devices.portexpander;

import org.bulldog.core.Signal;
import org.bulldog.core.gpio.Pin;
import org.bulldog.core.gpio.base.AbstractDigitalOutput;
import org.bulldog.core.util.BitMagic;

public class PCF8574DigitalOutput extends AbstractDigitalOutput {

	private PCF8574 expander;
	
	public PCF8574DigitalOutput(Pin pin, PCF8574 expander) {
		super(pin);
		this.expander = expander;
	}

	@Override
	protected void setupImpl() {
	}

	@Override
	protected void teardownImpl() {
	}

	@Override
	protected void applySignalImpl(Signal signal) {
		byte state = expander.getState();
		byte newState = BitMagic.setBit(state, getPin().getAddress(), signal.getNumericValue());
		expander.writeState(newState);
	}
	
	@Override
	public Signal getAppliedSignal() {
		return Signal.fromNumericValue(BitMagic.getBit(expander.getState(), getPin().getAddress()));
	}

}
