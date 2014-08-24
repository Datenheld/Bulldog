package org.bulldog.core.mocks;

import org.bulldog.core.pinfeatures.Pin;
import org.bulldog.core.pinfeatures.PinFeatureConfiguration;
import org.bulldog.core.pinfeatures.base.AbstractPinFeature;

public class MockedPinFeature1 extends AbstractPinFeature {

	public MockedPinFeature1(Pin pin) {
		super(pin);
	
	}

	@Override
	public String getName() {
		return "Mocked Feature 1";
	}

	@Override
	protected void setupImpl(PinFeatureConfiguration configuration) {
	}

	@Override
	protected void teardownImpl() {
	}
}
