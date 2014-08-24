package org.bulldog.raspberrypi.pinfeatures;

import org.bulldog.core.Signal;
import org.bulldog.core.pinfeatures.Pin;
import org.bulldog.core.pinfeatures.PinFeatureConfiguration;
import org.bulldog.linux.io.LinuxEpollListener;
import org.bulldog.linux.pinfeatures.LinuxDigitalInput;

public class RaspiDigitalInput extends LinuxDigitalInput implements LinuxEpollListener {

	public RaspiDigitalInput(Pin pin) {
		super(pin);
	}
	
	public Signal read() {
		return getSysFsPin().getValue();
	}

	public void setup(PinFeatureConfiguration configuration) {
		super.setup(configuration);
	}
	
}

