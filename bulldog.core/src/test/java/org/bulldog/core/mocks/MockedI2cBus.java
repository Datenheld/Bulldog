package org.bulldog.core.mocks;

import org.bulldog.core.gpio.Pin;
import org.bulldog.core.io.bus.I2cBus;

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

	@Override
	public int getFrequency() {
		// TODO Auto-generated method stub
		return 0;
	}

}
