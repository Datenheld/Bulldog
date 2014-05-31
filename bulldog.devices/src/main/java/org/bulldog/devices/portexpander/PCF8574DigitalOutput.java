package org.bulldog.devices.portexpander;

import org.bulldog.core.Signal;
import org.bulldog.core.gpio.Pin;
import org.bulldog.core.gpio.base.AbstractDigitalOutput;

public class PCF8574DigitalOutput extends AbstractDigitalOutput {

	private PCF8574 expander;
	
	public PCF8574DigitalOutput(Pin pin, PCF8574 expander) {
		super(pin);
		this.expander = expander;
	}

	@Override
	public void setup() {
	}

	@Override
	public void teardown() {
	}

	@Override
	protected void applySignalImpl(Signal signal) {
		byte state = expander.getLastKnownState();
		state ^= signal.getNumericValue() << getPin().getAddress();
		expander.writeState(state);
	}

}
