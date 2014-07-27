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
		
		DigitalOutput chipSelect1 =  board.getPin(BBBNames.P8_12).as(DigitalOutput.class);
		MCP4902 dac1 = new MCP4902(bus, chipSelect1);
		
		DigitalOutput chipSelect2 =  board.getPin(BBBNames.P8_14).as(DigitalOutput.class);
		MCP4902 dac2 = new MCP4902(bus, chipSelect2);
		
		//Before we can do anything, let's open the bus. This is only necessary if the bus
		//is directly used. Connections will attempt to open the bus if it is closed.
		bus.open();
		
		//Let's start with a broadcast - we can talk to any desired number of selected chips directly
		bus.broadcast(new byte[] { (byte)0xFF, (byte)0x00 }, chipSelect1, chipSelect2);
		
		//these commands will be routed through the SPI connections of the devices
		for(double d = 0; d < 1.0; d += 0.01) {
			dac1.setVoltageOnDacA(d, false);
			dac1.setVoltageOnDacB(d, false);
			dac2.setVoltageOnDacA(d, false);
			dac2.setVoltageOnDacB(d, false);
			BulldogUtil.sleepMs(10);
		}
		
		for(double d = 1.0; d > 0.0; d -= 0.01) {
			dac1.setVoltageOnDacA(d, false);
			dac1.setVoltageOnDacB(d, false);
			dac2.setVoltageOnDacA(d, false);
			dac2.setVoltageOnDacB(d, false);
			BulldogUtil.sleepMs(10);
		}
	
		for(double d = 0; d < 1.0; d += 0.01) {
			dac1.setVoltageOnDacA(d, false);
			dac1.setVoltageOnDacB(d, false);
			dac2.setVoltageOnDacA(d, false);
			dac2.setVoltageOnDacB(d, false);
			BulldogUtil.sleepMs(10);
		}
		
		dac1.shutdownDacA();
		BulldogUtil.sleepMs(500);
		dac1.shutdownDacB();
		BulldogUtil.sleepMs(500);
		dac2.shutdownDacA();
		BulldogUtil.sleepMs(500);
		dac2.shutdownDacB();
		BulldogUtil.sleepMs(500);

		
	}
}
