package org.bulldog.core.io;

import java.io.IOException;
import java.io.OutputStream;

import org.bulldog.core.Signal;
import org.bulldog.core.gpio.DigitalOutput;
import org.bulldog.core.util.BitMagic;

public class PinIOOutputStream extends OutputStream {

	private PinIOGroup group;
	
	public PinIOOutputStream(PinIOGroup group) {
		this.group = group;
	}
	
	@Override
	public void write(int b) throws IOException {
		group.startEnable();
		
		for(int i = 0; i < group.getDataPins().length; i++) {
			DigitalOutput out = group.getDataPins()[i];
			Signal signal = Signal.fromNumericValue(BitMagic.getBit(b, i));
			out.applySignal(signal);
		}
		
		group.endEnable();
	}

}
