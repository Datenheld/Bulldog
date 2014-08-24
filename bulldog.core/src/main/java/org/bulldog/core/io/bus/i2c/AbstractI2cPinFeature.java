package org.bulldog.core.io.bus.i2c;

import org.bulldog.core.gpio.Pin;
import org.bulldog.core.gpio.base.AbstractPinFeature;

public abstract class AbstractI2cPinFeature extends AbstractPinFeature implements I2cSda, I2cScl {

	private static final String NAME = "I2C %s on Pin %s";
	private I2cSignalType signalType;
	
	public AbstractI2cPinFeature(Pin pin, I2cSignalType signalType) {
		super(pin);
		this.signalType = signalType;
	}

	@Override
	public String getName() {
		return String.format(NAME, signalType, getPin().getName());
	}

}
