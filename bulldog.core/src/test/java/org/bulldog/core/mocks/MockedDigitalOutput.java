package org.bulldog.core.mocks;

import org.bulldog.core.Signal;
import org.bulldog.core.gpio.Pin;
import org.bulldog.core.gpio.base.AbstractDigitalOutput;

public class MockedDigitalOutput extends AbstractDigitalOutput {

	public MockedDigitalOutput(Pin pin) {
		super(pin);
	}

	@Override
	protected void setupImpl() {
	}

	@Override
	protected void teardownImpl() {
	}

	@Override
	protected void applySignalImpl(Signal signal) {
	}

}
