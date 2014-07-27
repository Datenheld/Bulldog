package org.bulldog.core.io.bus.spi;

import java.io.IOException;
import java.util.List;

import org.bulldog.core.gpio.DigitalOutput;
import org.bulldog.core.io.bus.Bus;


public interface SpiBus extends Bus {

	/*Pin getMISO();
	Pin getMOSI();
	Pin getSCLK(); */
	List<DigitalOutput> getSlaveSelectPins();
	
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
	
	void selectSlave(DigitalOutput chipSelect);
	void selectSlaves(DigitalOutput... chipSelects);
	void selectSlaves(Integer... chipSelectAddresses);
	
	SpiConnection createSpiConnection(int chipSelectAddress);
	SpiConnection createSpiConnection(DigitalOutput chipSelect);
	SpiConnection createSpiConnection(DigitalOutput... chipSelects);
	SpiConnection createSpiConnection(int...chipSelectAddress);
	
	void broadcast(byte[] bytes, DigitalOutput... chipSelects) throws IOException;
	
	SpiMessage transfer(byte[] data);
}
