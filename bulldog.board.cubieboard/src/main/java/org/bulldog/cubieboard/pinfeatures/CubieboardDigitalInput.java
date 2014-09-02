package org.bulldog.cubieboard.pinfeatures;

import org.bulldog.core.Signal;
import org.bulldog.core.pinfeatures.Pin;
import org.bulldog.core.pinfeatures.PinFeatureConfiguration;
import org.bulldog.core.pinfeatures.event.InterruptListener;
import org.bulldog.cubieboard.CubieboardPin;
import org.bulldog.linux.io.LinuxEpollListener;
import org.bulldog.linux.pinfeatures.LinuxDigitalInput;
import org.bulldog.linux.sysfs.SysFsPin;

public class CubieboardDigitalInput extends LinuxDigitalInput implements LinuxEpollListener {

    private final CubieboardGpioMemory gpioMemory;
    private final int pinIndex;
    private final int portIndex;

    public CubieboardDigitalInput(Pin pin, CubieboardGpioMemory gpioMemory, int portIndex) {
		super(pin);

        this.gpioMemory = gpioMemory;
        this.pinIndex = pin.getIndexOnPort();
        this.portIndex = portIndex;
    }

    @Override
    protected SysFsPin createSysFsPin(Pin pin) {
        return new CubieboardSysFsPin(pin.getAddress(), ((CubieboardPin)pin).getFsName(), ((CubieboardPin)pin).isInterrupt());
    }

    @Override
    public void setup(PinFeatureConfiguration configuration) {
        super.setup(configuration);
        gpioMemory.setPinDirection(portIndex, pinIndex, 0);
    }

    @Override
    public Signal read() {
        return Signal.fromNumericValue(gpioMemory.getPinValue(portIndex, pinIndex));
    }

    @Override
    public void addInterruptListener(InterruptListener listener) {
        checkInterruptsAllowed();
        super.addInterruptListener(listener);
    }

    @Override
    public void enableInterrupts() {
        checkInterruptsAllowed();
        super.enableInterrupts();
    }

    private void checkInterruptsAllowed() {
        if (!((CubieboardPin)getPin()).isInterrupt())
            throw new RuntimeException("Pin " + getName() + " is not connected to interrupt controller");
    }
}

