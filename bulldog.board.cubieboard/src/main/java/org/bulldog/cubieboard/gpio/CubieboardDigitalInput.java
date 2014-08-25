package org.bulldog.cubieboard.gpio;

import org.bulldog.core.gpio.Pin;
import org.bulldog.core.gpio.event.InterruptListener;
import org.bulldog.cubieboard.CubieboardPin;
import org.bulldog.linux.gpio.LinuxDigitalInput;
import org.bulldog.linux.io.LinuxEpollListener;
import org.bulldog.linux.sysfs.SysFsPin;

public class CubieboardDigitalInput extends LinuxDigitalInput implements LinuxEpollListener {

	public CubieboardDigitalInput(Pin pin) {
		super(pin);
	}

    @Override
    protected SysFsPin createSysFsPin(Pin pin) {
        return new CubieboardSysFsPin(pin.getAddress(), ((CubieboardPin)pin).getFsName(), ((CubieboardPin)pin).isInterrupt());
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

