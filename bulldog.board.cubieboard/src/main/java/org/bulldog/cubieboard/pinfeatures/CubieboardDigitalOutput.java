package org.bulldog.cubieboard.pinfeatures;

import org.bulldog.core.pinfeatures.Pin;
import org.bulldog.cubieboard.CubieboardPin;
import org.bulldog.linux.pinfeatures.LinuxDigitalOutput;
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
