package org.bulldog.examples;

import java.io.IOException;

import org.bulldog.beagleboneblack.BBBNames;
import org.bulldog.core.gpio.DigitalOutput;
import org.bulldog.core.io.bus.spi.SpiBus;
import org.bulldog.core.platform.Board;
import org.bulldog.core.platform.Platform;
import org.bulldog.core.util.BulldogUtil;
import org.bulldog.devices.dac.MCP4902;

public class SpiExample {

	public static void main(String... args) throws IOException {
		
		//Get your platform
		final Board board = Platform.createBoard();
    	
		SpiBus bus = board.getSpiBus(BBBNames.SPI_0);
		DigitalOutput chipSelect =  board.getPin(BBBNames.P8_12).as(DigitalOutput.class);
		MCP4902 digitalAnalogConverter = new MCP4902(bus, chipSelect);
		
		//digitalAnalogConverter.shutdownDacA();
		//digitalAnalogConverter.shutdownDacB();
		
		BulldogUtil.sleepMs(1000);
		
		while(true) {
			digitalAnalogConverter.setVoltageOnDacA(1.0, false);
			digitalAnalogConverter.setVoltageOnDacB(0.0, false);
			BulldogUtil.sleepMs(1000);
			digitalAnalogConverter.setVoltageOnDacA(0.0, false);
			digitalAnalogConverter.setVoltageOnDacB(1.0, false);
			BulldogUtil.sleepMs(1000);
		}
	}
}
