package org.bulldog.raspberrypi;

<<<<<<< HEAD
import org.bulldog.core.pinfeatures.Pin;
=======
import org.bulldog.core.gpio.Pin;
>>>>>>> origin/master

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
