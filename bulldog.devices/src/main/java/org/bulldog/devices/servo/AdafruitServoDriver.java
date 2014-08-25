package org.bulldog.devices.servo;

import org.bulldog.core.io.bus.i2c.I2cBus;
import org.bulldog.core.io.bus.i2c.I2cConnection;
import org.bulldog.devices.pwmdriver.PCA9685;

public class AdafruitServoDriver extends PCA9685 {

	private static final String NAME = "ADAFRUIT 16-CHANNEL 12-BIT PWM/SERVO DRIVER - I2C INTERFACE";
	
	public AdafruitServoDriver(I2cConnection connection) {
		super(connection);
		setName(NAME);
	}
	
	public AdafruitServoDriver(I2cBus bus, int address) {
		this(bus.createI2cConnection(address));
	}
}
