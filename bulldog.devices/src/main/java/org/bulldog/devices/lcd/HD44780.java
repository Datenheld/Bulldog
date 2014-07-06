package org.bulldog.devices.lcd;

import java.io.IOException;

import org.bulldog.core.Signal;
import org.bulldog.core.gpio.DigitalOutput;
import org.bulldog.core.io.PinIOGroup;

public class HD44780 {

	private PinIOGroup dataLine;
	private DigitalOutput rs;
	private DigitalOutput rw;
	
	public HD44780(DigitalOutput rs,
				   DigitalOutput rw,
				   PinIOGroup group) {
		this.dataLine = group;
		this.rs = rs;
		this.rw = rw;
	}

	
	public void init() throws IOException {
		rw.applySignal(Signal.Low);
		writeCommand(0x38); // function set: 
		 // 8-bit interface, 2 display lines, 5x7 font 
		writeCommand(0x06); // entry mode set: 
		 // increment automatically, no display shift 
		writeCommand(0x0E); // display control: 
		 // turn display on, cursor on, no blinking 
		writeCommand(0x01); // clear display, set cursor position to zero 
	}
	
	public void writeString(String text) throws IOException {
		byte[] bytes = text.getBytes();
		for(int i = 0; i < bytes.length; i++) {
			writeData(bytes[i]);
		}
	}
	
	public void writeCommand(int command) throws IOException {
		rs.applySignal(Signal.Low);
		dataLine.writeByte(command);
	}
	
	public void writeData(int data) throws IOException {
		rs.applySignal(Signal.High);
		dataLine.writeByte(data);
	}
	

}
