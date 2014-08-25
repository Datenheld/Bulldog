package org.bulldog.raspberrypi;

import org.bulldog.core.gpio.Pin;
import org.bulldog.core.gpio.base.DigitalIOFeature;
import org.bulldog.core.platform.AbstractBoard;
import org.bulldog.linux.io.LinuxSpiBus;
import org.bulldog.linux.sysinfo.CpuInfo;
import org.bulldog.raspberrypi.gpio.RaspiDigitalInput;
import org.bulldog.raspberrypi.gpio.RaspiDigitalOutput;
import org.bulldog.raspberrypi.gpio.RaspiPwm;
import org.bulldog.raspberrypi.io.RaspberryPiI2cBus;

public class RaspberryPi extends AbstractBoard {

	private static final String NAME = "Raspberry Pi";
		
	RaspberryPi() {
		super();
		if(getRevision() >= 4) {
			createPinsRev2();
		} else {
			createPinsRev1();
		}
		
		createIOPorts();
	}

	@Override
	public String getName() {
		return NAME;
	}
	
	private void createPinsRev1() {
		getPins().add(createDigitalIOPin(RaspiNames.P1_3, "P1", 3, 0));
		getPins().add(createDigitalIOPin(RaspiNames.P1_5, "P1", 5, 1));
		getPins().add(createDigitalIOPin(RaspiNames.P1_7, "P1", 7, 4));
		getPins().add(createDigitalIOPin(RaspiNames.P1_8, "P1", 8, 14));
		getPins().add(createDigitalIOPin(RaspiNames.P1_10, "P1", 10, 15));
		getPins().add(createDigitalIOPin(RaspiNames.P1_11, "P1", 11, 17));
		getPins().add(createDigitalIOPin(RaspiNames.P1_12, "P1", 12, 18));
		getPins().add(createDigitalIOPin(RaspiNames.P1_13, "P1", 13, 21));
		getPins().add(createDigitalIOPin(RaspiNames.P1_15, "P1", 15, 22));
		getPins().add(createDigitalIOPin(RaspiNames.P1_16, "P1", 16, 23));
		getPins().add(createDigitalIOPin(RaspiNames.P1_18, "P1", 18, 24));
		getPins().add(createDigitalIOPin(RaspiNames.P1_19, "P1", 19, 10));
		getPins().add(createDigitalIOPin(RaspiNames.P1_21, "P1", 21, 9));
		getPins().add(createDigitalIOPin(RaspiNames.P1_22, "P1", 22, 25));
		getPins().add(createDigitalIOPin(RaspiNames.P1_23, "P1", 23, 11));
		getPins().add(createDigitalIOPin(RaspiNames.P1_24, "P1", 24, 8));
		getPins().add(createDigitalIOPin(RaspiNames.P1_26, "P1", 26, 7));
		
		Pin pwmPin = getPin(RaspiNames.P1_12);
		pwmPin.addFeature(new RaspiPwm(pwmPin));
	}
	
	private void createPinsRev2() {
		getPins().add(createDigitalIOPin(RaspiNames.P1_3, "P1", 3, 2));
		getPins().add(createDigitalIOPin(RaspiNames.P1_5, "P1", 5, 3));
		getPins().add(createDigitalIOPin(RaspiNames.P1_7, "P1", 7, 4));
		getPins().add(createDigitalIOPin(RaspiNames.P1_8, "P1", 8, 14));
		getPins().add(createDigitalIOPin(RaspiNames.P1_10, "P1", 10, 15));
		getPins().add(createDigitalIOPin(RaspiNames.P1_11, "P1", 11, 17));
		getPins().add(createDigitalIOPin(RaspiNames.P1_12, "P1", 12, 18));
		getPins().add(createDigitalIOPin(RaspiNames.P1_13, "P1", 13, 27));
		getPins().add(createDigitalIOPin(RaspiNames.P1_15, "P1", 15, 22));
		getPins().add(createDigitalIOPin(RaspiNames.P1_16, "P1", 16, 23));
		getPins().add(createDigitalIOPin(RaspiNames.P1_18, "P1", 18, 24));
		getPins().add(createDigitalIOPin(RaspiNames.P1_19, "P1", 19, 10));
		getPins().add(createDigitalIOPin(RaspiNames.P1_21, "P1", 21, 9));
		getPins().add(createDigitalIOPin(RaspiNames.P1_22, "P1", 22, 25));
		getPins().add(createDigitalIOPin(RaspiNames.P1_23, "P1", 23, 11));
		getPins().add(createDigitalIOPin(RaspiNames.P1_24, "P1", 24, 8));
		getPins().add(createDigitalIOPin(RaspiNames.P1_26, "P1", 26, 7));
		
		getPins().add(createDigitalIOPin(RaspiNames.P5_3, "P5", 3, 28));
		getPins().add(createDigitalIOPin(RaspiNames.P5_4, "P5", 4, 29));
		getPins().add(createDigitalIOPin(RaspiNames.P5_5, "P5", 5, 30));
		getPins().add(createDigitalIOPin(RaspiNames.P5_6, "P5", 6, 31));
		
		Pin pwmPin = getPin(RaspiNames.P1_12);
		pwmPin.addFeature(new RaspiPwm(pwmPin));
	}
	
	private Pin createDigitalIOPin(String name, String port, int portIndex, int gpioAddress) {
		RaspberryPiPin pin = new RaspberryPiPin(name, gpioAddress, port, portIndex, gpioAddress);
		pin.addFeature(new DigitalIOFeature(pin, new RaspiDigitalInput(pin), new RaspiDigitalOutput(pin)));
		return pin;
	}
	
	private void createIOPorts() {
		getI2cBuses().add(new RaspberryPiI2cBus(RaspiNames.I2C_0, "/dev/i2c-0", getPin(RaspiNames.P1_3), getPin(RaspiNames.P1_5)));
		getSpiBuses().add(new LinuxSpiBus(RaspiNames.SPI_0_CS0, "/dev/spidev0.0", this));
		getSpiBuses().add(new LinuxSpiBus(RaspiNames.SPI_0_CS1, "/dev/spidev0.1", this));
	}
	
	private int getRevision() {
		String revision = CpuInfo.getCPURevision();
		if(revision.length() > 4) {
			revision = revision.substring(revision.length() - 4);
		}
		
		int numericRevision = Integer.parseInt(revision);
		return numericRevision;
	}
	
	@Override
	public void cleanup() {
		super.cleanup();
		BCM2835.cleanup();
	}
	
}
