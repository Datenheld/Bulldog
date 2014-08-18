package org.bulldog.core.io;

import java.io.IOException;
import java.io.InputStream;

import org.bulldog.core.Signal;
import org.bulldog.core.gpio.DigitalInput;
import org.bulldog.core.util.BitMagic;

public class PinIOInputStream extends InputStream {

	private PinIOGroup group;
	
	public PinIOInputStream(PinIOGroup group) {
		this.group = group;
	}
	
	@Override
	public int read() throws IOException {
		
		group.startEnable();
		
		int value = 0;
		for(int i = 0; i < group.getDataPins().length; i++) {
			DigitalInput in = group.getDataPins()[i];
			Signal signal = in.read();
			value = BitMagic.setBit(value, i, signal.getNumericValue());
		}
		
		group.endEnable();
		
		return value;
	}


}
