package org.bulldog.examples;

import java.io.IOException;
import java.util.Arrays;

import org.bulldog.beagleboneblack.BBBNames;
import org.bulldog.core.gpio.DigitalOutput;
import org.bulldog.core.io.bus.spi.SpiBus;
import org.bulldog.core.io.bus.spi.SpiConnection;
import org.bulldog.core.io.bus.spi.SpiMode;
import org.bulldog.core.platform.Board;
import org.bulldog.core.platform.Platform;
import org.bulldog.core.util.BulldogUtil;
import org.bulldog.devices.dac.MCP4902;

public class SpiExample {

	public static void main(String... args) throws IOException {
		
		//Get your platform
		final Board board = Platform.createBoard();
    	
		//Retrieve the bus object
		SpiBus bus = board.getSpiBus(BBBNames.SPI_1_CS0);
		
		//We shall handle our chip selects via bulldog. This gives us better flexibility.
		//and allows for more devices without multiplexing the internal CS lines.
		DigitalOutput chipSelect1 =  board.getPin(BBBNames.P8_12).as(DigitalOutput.class);
		MCP4902 dac1 = new MCP4902(bus, chipSelect1);
		
		DigitalOutput chipSelect2 =  board.getPin(BBBNames.P8_14).as(DigitalOutput.class);
		MCP4902 dac2 = new MCP4902(bus, chipSelect2);
		
		//If you want to use the builtin chip select feature, you needn't use 
		//any manual chip selects and you needn't select slaves at all.
		//But if you want to talk to more than one device you have to implement
		//your custom chip select multiplexing. This is something that libbulldog
		//does for you when you use the simple "manual" cs line feature.
		SpiConnection connection = bus.createSpiConnection();
		connection.writeByte(0xFF);
		
		
		//Before we can do anything, let's open the bus. This is only necessary if the bus
		//is directly used. Connections will attempt to open the bus if it is closed.
		bus.open();
		
		//Let's start with a broadcast - we can talk to any desired number of selected chips directly
		bus.broadcast(new byte[] { (byte)0xFF, (byte)0x00 }, chipSelect1, chipSelect2);
		
		//Let's write some bytes directly - first we select the slave
		//If you want to talk to multiple slaves the selectSlaveS method 
		//should be used - as the slave select is reset with every call.
		bus.selectSlave(chipSelect1);
		//bus.selectSlaves(chipSelect1, chipSelect2);
		bus.writeBytes(new byte[] { (byte)0x00, (byte)0x00 });
		
		//Let's play with the settings of the bus
		bus.setBitsPerWord(8);
		bus.writeByte(0xF0);
		
		//This mode is unsupported on the Beaglebone Black
		//You can use BitMagic.reverse to reverse the input
		//if necessary
		//bus.useLeastSignificantBitFirst();
		//bus.writeByte(0xF0);
		
		bus.useMostSignificantBitFirst();
		bus.setSpeedInHz(10000);
		bus.writeByte(0xF0);
		
		bus.setSpeedInHz(100000);
		bus.writeByte(0xF0);
		
		bus.setMode(SpiMode.Mode1);
		bus.writeByte(0xF0);
		
		//Reset it to something useful for the DAC
		bus.setMode(SpiMode.Mode0);
		bus.setBitsPerWord(8);
		bus.setSpeedInHz(4000000);
		bus.useMostSignificantBitFirst();
		
		byte[] bytes = new byte[4096];
		Arrays.fill(bytes, (byte)0xAA);
		bus.writeBytes(bytes);
		
		//These commands will be routed through the SPI connections of the devices
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
