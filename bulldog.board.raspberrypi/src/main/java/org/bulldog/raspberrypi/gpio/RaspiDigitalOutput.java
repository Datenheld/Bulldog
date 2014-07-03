package org.bulldog.raspberrypi.gpio;

import org.bulldog.core.Signal;
import org.bulldog.core.gpio.Pin;
import org.bulldog.core.gpio.base.AbstractDigitalOutput;
import org.bulldog.linux.io.mmap.MemoryMap;
import org.bulldog.raspberrypi.BTC2708Constants;
import org.bulldog.raspberrypi.RaspberryPiPin;

public class RaspiDigitalOutput extends AbstractDigitalOutput {
	
	private static MemoryMap memoryMap;
	
	public RaspiDigitalOutput(Pin pin) {
		super(pin);
	}

	@Override
	public void setup() {
		configureAsInput();
		configureAsOutput();
	}
	
	private int getRegisterAddress() {
		return (getRaspberryPiPin().getGpioAddress() % 10) * 3;
	}
	
	private void configureAsInput() {
		long address = getRaspberryPiPin().getGpioAddress() / 10;
		int value = getMemoryMap().getValueAt(address);
		value &= ~(7 << getRegisterAddress());
		getMemoryMap().setValue(address, value);
	}
	
	private void configureAsOutput() {
		long address = getRaspberryPiPin().getGpioAddress() / 10;
		int value = getMemoryMap().getValueAt(address);
		value |=  (1 << getRegisterAddress());
		getMemoryMap().setValue(address, value);
	}

	@Override
	public void teardown() {

	}

	@Override
	protected void applySignalImpl(Signal signal) {
		int value = 1 << getRaspberryPiPin().getGpioAddress();
		if(signal == Signal.High) {
			getMemoryMap().setValue(7, value);
		} else {
			getMemoryMap().setValue(10, value);
		}
	}
	
	protected static MemoryMap getMemoryMap() {
		if(memoryMap == null) {
			memoryMap = new MemoryMap("/dev/mem", BTC2708Constants.GPIO_BASE, 4096, 0);
		}
		
		return memoryMap;
	}

	private RaspberryPiPin getRaspberryPiPin() {
		RaspberryPiPin pin = (RaspberryPiPin)getPin();
		return pin;
	}
}
