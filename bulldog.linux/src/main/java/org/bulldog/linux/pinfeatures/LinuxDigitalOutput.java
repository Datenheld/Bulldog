package org.bulldog.linux.pinfeatures;

import org.bulldog.core.Signal;
import org.bulldog.core.pinfeatures.Pin;
import org.bulldog.core.pinfeatures.PinFeatureConfiguration;
import org.bulldog.core.pinfeatures.base.AbstractDigitalOutput;
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
	protected void setupImpl(PinFeatureConfiguration configuration) {
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
