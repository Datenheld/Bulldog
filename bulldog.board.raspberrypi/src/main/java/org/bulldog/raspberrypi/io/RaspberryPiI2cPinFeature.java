package org.bulldog.raspberrypi.io;

import org.bulldog.core.gpio.Pin;
import org.bulldog.core.io.bus.i2c.AbstractI2cPinFeature;
import org.bulldog.core.io.bus.i2c.I2cBus;
import org.bulldog.core.io.bus.i2c.I2cSignalType;

public class RaspberryPiI2cPinFeature extends AbstractI2cPinFeature {

	public RaspberryPiI2cPinFeature(I2cBus bus, Pin pin, I2cSignalType signalType) {
		super(pin, signalType);
	}


	@Override
	public boolean isBlocking() {
		return false;
	}

	@Override
	protected void setupImpl() {
	}

	@Override
	protected void teardownImpl() {
	}

	@Override
	public I2cBus getI2cBus() {
		return null;
	}

	
}
