package org.bulldog.raspberrypi.gpio;

import org.bulldog.core.Signal;
import org.bulldog.core.gpio.Pin;
import org.bulldog.core.gpio.base.AbstractDigitalOutput;
import org.bulldog.raspberrypi.BCM2835;
import org.bulldog.raspberrypi.RaspberryPiPin;

public class RaspiDigitalOutput extends AbstractDigitalOutput {
	
	public RaspiDigitalOutput(Pin pin) {
		super(pin);
	}

	@Override
	public void setup() {
		RaspberryPiPin pin = (RaspberryPiPin)getPin();
		BCM2835.configureAsInput(pin.getGpioAddress());
		BCM2835.configureAsOutput(pin.getGpioAddress());
	}
	

	@Override
	public void teardown() {

	}

	@Override
	protected void applySignalImpl(Signal signal) {
		int value = 1 << getRaspberryPiPin().getGpioAddress();
		if(signal == Signal.High) {
			BCM2835.getGpioMemory().setValue(7, value);
		} else {
			BCM2835.getGpioMemory().setValue(10, value);
		}
	}

	private RaspberryPiPin getRaspberryPiPin() {
		RaspberryPiPin pin = (RaspberryPiPin)getPin();
		return pin;
	}
}
