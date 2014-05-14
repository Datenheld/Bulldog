package org.bulldog.core.mocks;

import org.bulldog.core.bus.I2cBus;
import org.bulldog.core.gpio.Pin;

public class MockedI2cBus extends MockedBus implements I2cBus {

	public MockedI2cBus(String name) {
		super(name);
	}

	public Pin getSDA() {
		// TODO Auto-generated method stub
		return null;
	}

	public Pin getSCL() {
		// TODO Auto-generated method stub
		return null;
	}

}
