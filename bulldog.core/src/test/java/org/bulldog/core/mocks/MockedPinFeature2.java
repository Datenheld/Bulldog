package org.bulldog.core.mocks;

import org.bulldog.core.gpio.Pin;
import org.bulldog.core.gpio.base.AbstractPinFeature;

public class MockedPinFeature2 extends AbstractPinFeature {

	private boolean blocking = false;;
	
	public MockedPinFeature2(Pin pin) {
		super(pin);
	
	}

	@Override
	public String getName() {
		return "Mocked Feature";
	}

	@Override
	public boolean isBlocking() {
		return blocking;
	}
	
	public void setBlocking(boolean blocking) {
		this.blocking = blocking;
	}

	@Override
	public void setup() {

	}

	@Override
	public void teardown() {
	}
}

