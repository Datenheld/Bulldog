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
	protected void setupImpl() {
		RaspberryPiPin pin = (RaspberryPiPin)getPin();
		BCM2835.configureAsInput(pin.getGpioNumber());
		BCM2835.configureAsOutput(pin.getGpioNumber());
	}
	

	@Override
	protected void teardownImpl() {

	}

	@Override
	protected void applySignalImpl(Signal signal) {
		int value = 1 << getRaspberryPiPin().getGpioNumber();
		if(signal == Signal.High) {
			BCM2835.getGpioMemory().setIntValue(BCM2835.GPIO_SET, value);
		} else {
			BCM2835.getGpioMemory().setIntValue(BCM2835.GPIO_CLEAR, value);
		}
	}

	private RaspberryPiPin getRaspberryPiPin() {
		RaspberryPiPin pin = (RaspberryPiPin)getPin();
		return pin;
	}
}
