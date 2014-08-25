package org.bulldog.linux.gpio;

import org.bulldog.core.Signal;
import org.bulldog.core.gpio.Pin;
import org.bulldog.core.gpio.base.AbstractDigitalOutput;
import org.bulldog.linux.sysfs.SysFsPin;

public class LinuxDigitalOutput extends AbstractDigitalOutput {

	private SysFsPin sysFsPin;
	
	public LinuxDigitalOutput(Pin pin) {
		super(pin);
		sysFsPin = createSysFsPin(pin);
	}

    protected SysFsPin createSysFsPin(Pin pin) {
        return new SysFsPin(pin.getAddress());
    }

    @Override
	protected void setupImpl() {
		exportPinIfNecessary();
	}

	@Override
	protected void teardownImpl() {
		unexportPin();
	}
	
	protected void exportPinIfNecessary() {
		sysFsPin.exportIfNecessary();
		sysFsPin.setDirection("out");
	}
	
	protected void unexportPin() {
		sysFsPin.unexport();
	}

	@Override
	protected void applySignalImpl(Signal signal) {
		sysFsPin.setValue(signal);
	}

}
