package org.bulldog.core.io.bus.spi;

import java.util.List;

import org.bulldog.core.Polarity;
import org.bulldog.core.gpio.Pin;
import org.bulldog.core.io.bus.Bus;


public interface SpiBus extends Bus {

	Pin getMISO();
	Pin getMOSI();
	Pin getSCLK();
	List<Pin> getSS();
	
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
	
	SpiConnection createSpiConnection(int address);
	SpiConnection createSpiConnection(Pin pin);
}
