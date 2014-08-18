package org.bulldog.raspberrypi.gpio;

import org.bulldog.core.Signal;
import org.bulldog.core.gpio.Pin;
import org.bulldog.linux.gpio.LinuxDigitalInput;
import org.bulldog.linux.io.LinuxEpollListener;

public class RaspiDigitalInput extends LinuxDigitalInput implements LinuxEpollListener {

	public RaspiDigitalInput(Pin pin) {
		super(pin);
	}
	
	public Signal read() {
		return getSysFsPin().getValue();
	}

	public void setup() {
		super.setup();
	}
	
}

