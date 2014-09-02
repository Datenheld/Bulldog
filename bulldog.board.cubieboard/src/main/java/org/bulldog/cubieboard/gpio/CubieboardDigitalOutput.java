package org.bulldog.cubieboard.gpio;

import org.bulldog.core.Signal;
import org.bulldog.core.gpio.Pin;
import org.bulldog.cubieboard.CubieboardPin;
import org.bulldog.linux.gpio.LinuxDigitalOutput;
import org.bulldog.linux.sysfs.SysFsPin;

public class CubieboardDigitalOutput extends LinuxDigitalOutput {

    private final CubieboardGpioMemory gpioMemory;
    private final int pinIndex;
    private final int portIndex;

    public CubieboardDigitalOutput(Pin pin, CubieboardGpioMemory gpioMemory, int portIndex) {
		super(pin);

        this.gpioMemory = gpioMemory;
        this.pinIndex = pin.getIndexOnPort();
        this.portIndex = portIndex;
    }

    @Override
    protected SysFsPin createSysFsPin(Pin pin) {
        return new CubieboardSysFsPin(pin.getAddress(), ((CubieboardPin)pin).getFsName(), false);
    }

    @Override
    public void setup() {
        super.setup();
        gpioMemory.setPinDirection(portIndex, pinIndex, 1);
    }

    @Override
    protected void applySignalImpl(Signal signal) {
        gpioMemory.setPinValue(portIndex, pinIndex, signal.getNumericValue());
    }
}
