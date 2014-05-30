package org.bulldog.core.mocks;

import org.bulldog.core.gpio.Pin;
import org.bulldog.core.io.bus.i2c.I2cBus;

public class MockedI2cBus extends MockedBus implements I2cBus {

	public MockedI2cBus(String name) {
		super(name);
	}

	public Pin getSDA() {
		return null;
	}

	public Pin getSCL() {
		return null;
	}

	@Override
	public int getFrequency() {
		return 0;
	}

}
