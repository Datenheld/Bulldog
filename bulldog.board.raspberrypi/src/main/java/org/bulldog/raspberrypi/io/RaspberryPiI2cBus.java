package org.bulldog.raspberrypi.io;

import org.bulldog.core.gpio.Pin;
import org.bulldog.core.io.bus.i2c.I2cSignalType;
import org.bulldog.linux.io.LinuxI2cBus;

public class RaspberryPiI2cBus extends LinuxI2cBus {

	private Pin sdaPin;
	private Pin sclPin;
	
	public RaspberryPiI2cBus(String name, String deviceFilePath, Pin sdaPin, Pin sclPin) {
		super(name, deviceFilePath);
		this.sdaPin = sdaPin;
		this.sclPin = sclPin;
		sdaPin.addFeature(new RaspberryPiI2cPinFeature(this, sdaPin, I2cSignalType.SDA));
		sclPin.addFeature(new RaspberryPiI2cPinFeature(this, sclPin, I2cSignalType.SCL));
	}

	public Pin getSCL() {
		return sclPin;
	}

	public Pin getSDA() {
		return sdaPin;
	}
}
