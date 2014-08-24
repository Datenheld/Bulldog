package org.bulldog.core.mocks;

import org.bulldog.core.Signal;
import org.bulldog.core.pinfeatures.Pin;
import org.bulldog.core.pinfeatures.PinFeatureConfiguration;
import org.bulldog.core.pinfeatures.base.AbstractDigitalOutput;

public class MockedDigitalOutput extends AbstractDigitalOutput {

	public MockedDigitalOutput(Pin pin) {
		super(pin);
	}

	@Override
	protected void setupImpl(PinFeatureConfiguration configuration) {
	}

	@Override
	protected void teardownImpl() {
	}

	@Override
	protected void applySignalImpl(Signal signal) {
	}

}
