<<<<<<< HEAD:bulldog.board.raspberrypi/src/main/java/org/bulldog/raspberrypi/pinfeatures/RaspiDigitalInput.java
package org.bulldog.raspberrypi.pinfeatures;

import org.bulldog.core.Signal;
import org.bulldog.core.pinfeatures.Pin;
import org.bulldog.core.pinfeatures.PinFeatureConfiguration;
import org.bulldog.linux.io.LinuxEpollListener;
import org.bulldog.linux.pinfeatures.LinuxDigitalInput;
=======
package org.bulldog.raspberrypi.gpio;

import org.bulldog.core.Signal;
import org.bulldog.core.gpio.Pin;
import org.bulldog.linux.gpio.LinuxDigitalInput;
import org.bulldog.linux.io.LinuxEpollListener;
>>>>>>> origin/master:bulldog.board.raspberrypi/src/main/java/org/bulldog/raspberrypi/gpio/RaspiDigitalInput.java

public class RaspiDigitalInput extends LinuxDigitalInput implements LinuxEpollListener {

	public RaspiDigitalInput(Pin pin) {
		super(pin);
	}
	
	public Signal read() {
		return getSysFsPin().getValue();
	}

<<<<<<< HEAD:bulldog.board.raspberrypi/src/main/java/org/bulldog/raspberrypi/pinfeatures/RaspiDigitalInput.java
	public void setup(PinFeatureConfiguration configuration) {
		super.setup(configuration);
=======
	public void setup() {
		super.setup();
>>>>>>> origin/master:bulldog.board.raspberrypi/src/main/java/org/bulldog/raspberrypi/gpio/RaspiDigitalInput.java
	}
	
}

