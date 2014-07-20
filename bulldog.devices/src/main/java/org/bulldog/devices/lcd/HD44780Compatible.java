package org.bulldog.devices.lcd;

import java.io.IOException;

import org.bulldog.core.Signal;
import org.bulldog.core.gpio.DigitalIO;
import org.bulldog.core.gpio.DigitalOutput;
import org.bulldog.core.io.PinIOGroup;
import org.bulldog.core.util.BitMagic;
import org.bulldog.core.util.BulldogUtil;

public class HD44780Compatible implements Lcd {

	private static final int CMD_CLEAR = 0x01;
	private static final int CMD_HOME = 0b00000010;
	
	private PinIOGroup dataLine;
	private DigitalOutput rs;
	private DigitalOutput rw;
	
	private boolean blinkCursor = false;
	private boolean showCursor = false;
	private HD44780Mode mode = HD44780Mode.EightBit;
	
	public HD44780Compatible(DigitalIO rs, DigitalIO rw, DigitalIO enable,
											   DigitalIO db4,
											   DigitalIO db5,
											   DigitalIO db6,
											   DigitalIO db7) {
		this(rs, rw, new PinIOGroup(enable, 0, db4, db5, db6, db7), HD44780Mode.FourBit);
	}
	
	public HD44780Compatible(DigitalIO rs, DigitalIO rw, DigitalIO enable, 
											   DigitalIO db0, 
											   DigitalIO db1, 
											   DigitalIO db2, 
											   DigitalIO db3,
											   DigitalIO db4,
											   DigitalIO db5,
											   DigitalIO db6,
											   DigitalIO db7) {
		this(rs, rw, new PinIOGroup(enable, 0, db0, db1, db2, db3, db4, db5, db6, db7), HD44780Mode.EightBit);
	}
	
	
	public HD44780Compatible(DigitalOutput rs, DigitalOutput rw, PinIOGroup group, HD44780Mode mode) {
		this.dataLine = group;
		this.rs = rs;
		this.rw = rw;
		showCursor = true;
		blinkCursor = true;
		this.mode = mode;
	}
	
	
	@Override
	public void setMode(int lines, LcdFont font) {
		if(mode == HD44780Mode.EightBit) {
			writeCommand(0b00110000);
			BulldogUtil.sleepMs(20);
			writeCommand(0b00110000);
			BulldogUtil.sleepMs(1);
			writeCommand(0b00110000);
			BulldogUtil.sleepMs(1);
		} else {
			writeCommand(0b00000011);
			BulldogUtil.sleepMs(20);
			writeCommand(0b00000011);
			BulldogUtil.sleepMs(1);
			writeCommand(0b00000011);
			BulldogUtil.sleepMs(1);
			writeCommand(0b0010);
		}
		clear();
		functionSet(lines, font);
		off();
		writeCommand(0x06);
		on();
	}
	
	public void home() {
		writeCommand(CMD_HOME);
		BulldogUtil.sleepMs(2);
	}
	
	private void functionSet(int lines, LcdFont font) {
		byte command = 0b00100000;
		if(mode == HD44780Mode.FourBit) {
			command = BitMagic.setBit(command, 4, 0);
		} else {
			command = BitMagic.setBit(command, 4, 1);
		}
		
		if(lines == 2) {
			command = BitMagic.setBit(command, 3, 1);
		} else {
			command = BitMagic.setBit(command, 3, 0);
		}
		
		if(font == LcdFont.Font_8x10) {
			command = BitMagic.setBit(command, 2, 1);
		} else {
			command = BitMagic.setBit(command, 2, 0);
		}
		
		writeCommand(command);
	}
	
	private void writeByteAsNibbles(int data) throws IOException {
		byte nibble = (byte)(((data & 0xF0) >> 4));
		dataLine.writeByte(nibble);
		nibble = (byte)((data & 0x0F));
		dataLine.writeByte(nibble);
	}
	
	private void writeToDisplay(int data) {
		try {
			if(this.mode == HD44780Mode.FourBit) {
				writeByteAsNibbles(data);
			} else {
				dataLine.writeByte(data);
			}
		} catch(Exception ex) {
			throw new RuntimeException(ex);
		}
	}
		
	private void writeCommand(int command)  {
		rw.applySignal(Signal.Low);
		rs.applySignal(Signal.Low);
		writeToDisplay(command);
		BulldogUtil.sleepMs(5);
	}
	
	private void writeData(int data) {
		rw.applySignal(Signal.Low);
		rs.applySignal(Signal.High);
		writeToDisplay(data);
	}
	
	public void clear() {
		writeCommand(CMD_CLEAR);
	}
	
	public void on()  {
		byte command = 0b1100;
		if(showCursor) {
			command = BitMagic.setBit(command, 1, 1);
		} 
		
		if(blinkCursor) {
			command = BitMagic.setBit(command, 0, 1);
		}
		
		writeCommand(command);
	}
	
	public void off() {
		byte command = 0b1000;
		writeCommand(command);
	}



	@Override
	public void writeAt(int row, int column, String text) {
		
	}

	@Override
	public String read() {
		return null;
	}


	@Override
	public void blinkCursor(boolean blink) {
		blinkCursor = blink;
		on();
	}

	@Override
	public void showCursor(boolean show) {
		showCursor = show;
		on();
	}
	
	@Override
	public void write(String text) {
		byte[] bytes = text.getBytes();
		for(int i = 0; i < bytes.length; i++) {
			writeData(bytes[i]);
		}
	}

	@Override
	public void setCursorPosition(int row, int column) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String read(int row, int column, int length) {
		// TODO Auto-generated method stub
		return null;
	}

}
