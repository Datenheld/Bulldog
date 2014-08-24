package org.bulldog.core.mocks;

import org.bulldog.core.pinfeatures.Pin;
import org.bulldog.core.pinfeatures.PinFeatureConfiguration;
import org.bulldog.core.pinfeatures.base.AbstractPinFeature;

public class MockedPinFeature2 extends AbstractPinFeature {
	
	public MockedPinFeature2(Pin pin) {
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

