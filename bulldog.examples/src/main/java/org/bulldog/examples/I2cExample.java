package org.bulldog.examples;

import java.io.IOException;

import org.bulldog.beagleboneblack.BBBNames;
import org.bulldog.core.io.bus.i2c.I2cBus;
import org.bulldog.core.platform.Board;
import org.bulldog.core.platform.Platform;
import org.bulldog.core.util.BulldogUtil;
import org.bulldog.devices.sensors.BH1750LightIntensitySensor;

public class I2cExample {
	
	public static void main(String... args) throws IOException {
		
		//Get your platform
		final Board board = Platform.createBoard();
    	
		I2cBus bus = board.getI2cBus(BBBNames.I2C_1);
		
		//Let's assume we have got a device on address xx
		BH1750LightIntensitySensor sensor = new BH1750LightIntensitySensor(bus, 0x23);
		sensor.initMode(BH1750LightIntensitySensor.MODE_HIGH_RES_05_LX_CONTINUOUS);
		
		while(true) {
			double luminosity = sensor.readLuminanceNormalized();
			System.out.println(luminosity + "[lx normalized]");
			BulldogUtil.sleepMs(100);
		}
	}
}
