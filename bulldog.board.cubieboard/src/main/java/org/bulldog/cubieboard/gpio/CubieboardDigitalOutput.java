package org.bulldog.cubieboard.gpio;

import org.bulldog.core.gpio.Pin;
import org.bulldog.cubieboard.CubieboardPin;
import org.bulldog.linux.gpio.LinuxDigitalOutput;
import org.bulldog.linux.sysfs.SysFsPin;

public class CubieboardDigitalOutput extends LinuxDigitalOutput {
	
	public CubieboardDigitalOutput(Pin pin) {
		super(pin);
	}

    @Override
    protected SysFsPin createSysFsPin(Pin pin) {
        return new CubieboardSysFsPin(pin.getAddress(), ((CubieboardPin)pin).getFsName(), false);
    }
}
