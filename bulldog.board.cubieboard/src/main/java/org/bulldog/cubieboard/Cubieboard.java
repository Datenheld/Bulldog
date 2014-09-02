package org.bulldog.cubieboard;

import org.bulldog.core.gpio.Pin;
import org.bulldog.core.gpio.base.DigitalIOFeature;
import org.bulldog.core.platform.AbstractBoard;
import org.bulldog.cubieboard.gpio.CubieboardDigitalInput;
import org.bulldog.cubieboard.gpio.CubieboardDigitalOutput;
import org.bulldog.cubieboard.gpio.CubieboardGpioMemory;
import org.bulldog.linux.util.LinuxLibraryLoader;

public class Cubieboard extends AbstractBoard {

	private static final String NAME = "Cubieboard";

	private static Cubieboard instance;

    private CubieboardGpioMemory gpioMemory;
	
	public static Cubieboard getInstance() {
		if(instance == null) {
			LinuxLibraryLoader.loadNativeLibrary();
			instance = new Cubieboard();
		}
		
		return instance;
	}
	
	private Cubieboard() {
		super();

        gpioMemory = new CubieboardGpioMemory();
        createPins();
	}

    @Override
    public void cleanup() {
        super.cleanup();
        gpioMemory.cleanup();
    }

    @Override
	public String getName() {
		return NAME;
	}

	private void createPins() {
        // PB
        getPins().add(createDigitalIOPin(CubieboardNames.PB18, 3, "B", 18, "3_pb18", false));
        // PG
		getPins().add(createDigitalIOPin(CubieboardNames.PG0, 9, "G", 0, "9_pg0", false));
		getPins().add(createDigitalIOPin(CubieboardNames.PG1, 7, "G", 1, "7_pg1", false));
		getPins().add(createDigitalIOPin(CubieboardNames.PG2, 8, "G", 2, "8_pg2", false));
		getPins().add(createDigitalIOPin(CubieboardNames.PG4, 6, "G", 4, "6_pg4", false));
		getPins().add(createDigitalIOPin(CubieboardNames.PG5, 5, "G", 5, "5_pg5", false));
		getPins().add(createDigitalIOPin(CubieboardNames.PG6, 4, "G", 6, "4_pg6", false));
		getPins().add(createDigitalIOPin(CubieboardNames.PG7, 19, "G", 7, "4_pg7", false));
		getPins().add(createDigitalIOPin(CubieboardNames.PG8, 18, "G", 8, "4_pg8", false));
		getPins().add(createDigitalIOPin(CubieboardNames.PG9, 17, "G", 9, "4_pg9", false));
		getPins().add(createDigitalIOPin(CubieboardNames.PG10, 16, "G", 10, "4_pg10", false));
		getPins().add(createDigitalIOPin(CubieboardNames.PG11, 15, "G", 11, "4_pg11", false));
	}

	public Pin createDigitalIOPin(String name, int address, String port, int indexOnPort, String fsName, boolean interrupt) {
		CubieboardPin pin = new CubieboardPin(name, address, port, indexOnPort, fsName, interrupt);

        int portIndex = port.charAt(0) - 'A';
        CubieboardDigitalInput input = new CubieboardDigitalInput(pin, gpioMemory, portIndex);
        CubieboardDigitalOutput output = new CubieboardDigitalOutput(pin, gpioMemory, portIndex);
        pin.addFeature(new DigitalIOFeature(pin, input, output));

        return pin;
	}
}
