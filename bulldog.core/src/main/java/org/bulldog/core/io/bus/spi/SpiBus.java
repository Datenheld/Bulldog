package org.bulldog.core.io.bus.spi;

import java.io.IOException;
import java.util.List;

import org.bulldog.core.Polarity;
import org.bulldog.core.gpio.DigitalOutput;
import org.bulldog.core.io.bus.Bus;


public interface SpiBus extends Bus {

	/*Pin getMISO();
	Pin getMOSI();
	Pin getSCLK(); */
	List<DigitalOutput> getSlaveSelectPins();
	List<DigitalOutput> getSelectedSlaveSelectPins();
	
	/*
	int getFrequency();
	void setFrequency(float frequency);
	
	void setBitsPerWord(int bpw);
	int getBitsPerWord();	
	
	void setDelayMicroseconds(int delay);
	int getDelayMicroseconds();
	
	void setClockphase();
	int getClockphase();
	
	void setMode(int mode);
	int getMode();
	
	void setClockPolarity(Polarity polarity);
	Polarity getClockPolarity();
	
	void setLeastSignificantBitFirst(boolean first);
	boolean getLeastSignificantBitFirst();*/
	
	void registerSlave(DigitalOutput output);
	void deregisterSlave(DigitalOutput output);
	void registerSlave(int address);
	void deregisterSlave(int address);
	
	/**
	 * This method deselects a slave
	 * 
	 * @param address The address of the pin that is used for chip select
	 */
	void deselectSlave(int address);
	void selectSlave(DigitalOutput chipSelect);
	void deselectSlave(DigitalOutput chipSelect);
	
	SpiConnection createSpiConnection(int address);
	SpiConnection createSpiConnection(DigitalOutput chipSelect);
	
	void broadcast(byte data) throws IOException;
	void broadcast(byte[] data) throws IOException;
	
	SpiMessage transfer(byte[] data);
}
