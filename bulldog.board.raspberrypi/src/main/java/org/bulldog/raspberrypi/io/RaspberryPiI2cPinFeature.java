package org.bulldog.raspberrypi.io;

<<<<<<< HEAD
import org.bulldog.core.io.bus.i2c.AbstractI2cPinFeature;
import org.bulldog.core.io.bus.i2c.I2cBus;
import org.bulldog.core.io.bus.i2c.I2cSignalType;
import org.bulldog.core.pinfeatures.Pin;
import org.bulldog.core.pinfeatures.PinFeatureConfiguration;
=======
import org.bulldog.core.gpio.Pin;
import org.bulldog.core.io.bus.i2c.AbstractI2cPinFeature;
import org.bulldog.core.io.bus.i2c.I2cBus;
import org.bulldog.core.io.bus.i2c.I2cSignalType;
>>>>>>> origin/master

public class RaspberryPiI2cPinFeature extends AbstractI2cPinFeature {

	public RaspberryPiI2cPinFeature(I2cBus bus, Pin pin, I2cSignalType signalType) {
		super(pin, signalType);
	}


	@Override
	public boolean isBlocking() {
		return false;
	}

	@Override
<<<<<<< HEAD
	protected void setupImpl(PinFeatureConfiguration configuration) {
=======
	protected void setupImpl() {
>>>>>>> origin/master
	}

	@Override
	protected void teardownImpl() {
	}

	@Override
	public I2cBus getI2cBus() {
		return null;
	}

	
}
