package org.bulldog.raspberrypi;

import org.bulldog.core.gpio.Pin;

public class RaspberryPiPin extends Pin {

	private int gpioAddress;
	
	public RaspberryPiPin(String name, int address, String port, int indexOnPort, int gpioNumber) {
		super(name, address, port, indexOnPort);
		this.gpioAddress = gpioNumber;
	}

	public int getGpioNumber() {
		return gpioAddress;
	}
}
