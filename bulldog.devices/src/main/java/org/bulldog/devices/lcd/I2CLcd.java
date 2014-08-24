package org.bulldog.devices.lcd;

import java.io.IOException;

import org.bulldog.core.io.bus.i2c.I2cBus;
import org.bulldog.core.io.bus.i2c.I2cConnection;
import org.bulldog.core.util.BulldogUtil;


/**
 * This class represents a HD44780 compatible lcd which is driven
 * in 4 BIT mode behind an I2C Port Expander.
 * 
 * @author Datenheld
 *
 */
public class I2CLcd implements Lcd {

	public static final byte ENABLE 		= 0b01000000;
	public static final byte DISABLE	 	= 0b00111111;
	public static final byte COMMAND 		= 0b00000000;
	public static final byte DATA			= 0b00100000;
	public static final byte INIT_4BIT_MODE	= 0b00000010;
	
	private I2cConnection connection;
	
	public I2CLcd(I2cBus bus, int address) throws IOException {
		this(bus.createI2cConnection(address));
	}
	
	public I2CLcd(I2cConnection connection) throws IOException {
		this.connection = connection;
		initialize();
	}

	public void initialize() throws IOException {
		writeByte(INIT_4BIT_MODE);
		writeCommand(0x28);
		writeCommand(0x0C);
		writeCommand(0x06);
		writeCommand(0x01);
		writeCommand(0x0f);
	}

	public void write(String string) {
		for(int i = 0; i < string.length(); i++) {
			writeData(string.charAt(i));
		}
	}
	
	public void clear() {
	}
	
	public void writeByte(byte data) { 
		try {
			connection.writeByte(data | ENABLE);
			connection.writeByte(data & DISABLE);
		} catch(Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	private void writeByteAsNibbles(int data, int mask) {
		byte nibble = (byte)(mask | ((data & 0xF0) >> 4));
		writeByte(nibble);
		nibble = (byte)(mask | (data & 0x0F));
		writeByte(nibble);
	}
	
	private void writeCommand(int data) {
		writeByteAsNibbles(data, COMMAND);
		BulldogUtil.sleepMs(5);
	}

	private void writeData(int data) {
		writeByteAsNibbles(data, DATA);
	}

	@Override
	public void setMode(LcdMode mode, LcdFont font) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void writeAt(int row, int column, String text) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void blinkCursor(boolean blink) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showCursor(boolean show) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void home() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void on() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void off() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setCursorPosition(int line, int column) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String readLine(int line) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String read(int length) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String read(int line, int column, int length) {
		// TODO Auto-generated method stub
		return null;
	}
}
