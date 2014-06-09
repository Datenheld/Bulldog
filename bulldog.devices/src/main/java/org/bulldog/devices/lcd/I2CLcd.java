package org.bulldog.devices.lcd;

import java.io.IOException;

import org.bulldog.core.io.bus.i2c.I2cBus;
import org.bulldog.core.io.bus.i2c.I2cConnection;


/**
 * This class represents a HD44780 compatible lcd which is driven
 * in 4 BIT mode behind an I2C Port Expander.
 * 
 * @author Datenheld
 *
 */
public class I2CLcd {

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

	public void write(String string) throws IOException {
		for(int i = 0; i < string.length(); i++) {
			writeData(string.charAt(i));
		}
	}
	
	public void clear() {
	}
	
	public void writeByte(byte data) throws IOException {
		connection.writeByte(data | ENABLE);
		connection.writeByte(data & DISABLE);
	}

	private void writeByteAsNibbles(int data, int mask) throws IOException {
		byte nibble = (byte)(mask | ((data & 0xF0) >> 4));
		writeByte(nibble);
		nibble = (byte)(mask | (data & 0x0F));
		writeByte(nibble);
	}
	
	private void writeCommand(int data) throws IOException {
		writeByteAsNibbles(data, COMMAND);
		sleep(5);
	}

	private void writeData(int data) throws IOException {
		writeByteAsNibbles(data, DATA);
	}
	
	private void sleep(int milliseconds) {
		try {
			Thread.sleep(milliseconds);
		} catch(Exception ex) {
			
		}
	}
}
