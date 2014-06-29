package org.bulldog.beagleboneblack.io;

import org.bulldog.core.gpio.Pin;
import org.bulldog.core.io.bus.i2c.AbstractI2cPinFeature;
import org.bulldog.core.io.bus.i2c.I2cBus;
import org.bulldog.core.io.bus.i2c.I2cSignalType;

public class BBBI2cPinFeature extends AbstractI2cPinFeature {

	public BBBI2cPinFeature(I2cBus bus, Pin pin, I2cSignalType signalType) {
		super(pin, signalType);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isBlocking() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setup() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void teardown() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public I2cBus getI2cBus() {
		// TODO Auto-generated method stub
		return null;
	}

}
