package org.bulldog.devices.portexpander;

import org.bulldog.core.Signal;
import org.bulldog.core.pinfeatures.Pin;
import org.bulldog.core.pinfeatures.PinFeatureConfiguration;
import org.bulldog.core.pinfeatures.base.AbstractDigitalInput;
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
	protected void setupImpl(PinFeatureConfiguration configuration) {
		byte state = expander.getState();
		byte newState = BitMagic.setBit(state, getPin().getAddress(), 1);
		expander.writeState(newState);
	}

	@Override
	protected void teardownImpl() {
	}


	@Override
	protected void enableInterruptsImpl() {}

	@Override
	protected void disableInterruptsImpl() {}
}
