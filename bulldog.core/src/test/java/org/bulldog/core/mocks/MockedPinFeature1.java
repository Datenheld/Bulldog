package org.bulldog.core.mocks;

import org.bulldog.core.gpio.Pin;
import org.bulldog.core.gpio.PinFeatureConfiguration;
import org.bulldog.core.gpio.base.AbstractPinFeature;

public class MockedPinFeature1 extends AbstractPinFeature {

	public MockedPinFeature1(Pin pin) {
		super(pin);
	
	}

	@Override
	public String getName() {
		return "Mocked Feature";
	}

	@Override
	protected void setupImpl(PinFeatureConfiguration configuration) {
	}

	@Override
	protected void teardownImpl() {
	}
}
