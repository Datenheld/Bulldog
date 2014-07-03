package org.bulldog.raspberrypi;

import org.bulldog.core.gpio.Pin;
import org.bulldog.core.platform.AbstractBoard;
import org.bulldog.linux.util.LinuxLibraryLoader;
import org.bulldog.raspberrypi.gpio.RaspiDigitalOutput;

public class RaspberryPi extends AbstractBoard {

	private static final String NAME = "Raspberry Pi";
	
	private static RaspberryPi instance;
	
	public static RaspberryPi getInstance() {
		if(instance == null) {
			LinuxLibraryLoader.loadNativeLibrary();
			instance = new RaspberryPi();
		}
		
		return instance;
	}
	
	private RaspberryPi() {
		createPins();
	}

	@Override
	public String getName() {
		return NAME;
	}
	
	private void createPins() {
		getPins().add(createDigitalIOPin("P1_7", "P1", 7, 4));
	}
	
	private Pin createDigitalIOPin(String name, String port, int portIndex, int gpioAddress) {
		RaspberryPiPin pin = new RaspberryPiPin(name, gpioAddress, port, portIndex, gpioAddress);
		pin.addFeature(new RaspiDigitalOutput(pin));
		return pin;
	}
	
}
