package org.bulldog.core.io.bus.i2c;

import org.bulldog.core.gpio.PinFeature;

public interface I2cPinFeature extends PinFeature {
	
	I2cBus getI2cBus();
}
