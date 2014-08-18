package org.bulldog.core.io.bus.i2c;

import java.io.IOException;

import org.bulldog.core.io.bus.BusConnection;

public class I2cConnection extends BusConnection {

	public I2cConnection(I2cBus bus, int address) {
		super(bus, address);
	}
	
	public byte readByteFromRegister(int register) throws IOException {
		writeByte((byte)register);
		return readByte();
	}
	
	public int readBytesFromRegister(int register, byte[] buffer) throws IOException {
		writeByte((byte)register);
		return readBytes(buffer);
	}
	
	public void writeByteToRegister(int register, int data) throws IOException {
		writeBytes(new byte[] { (byte)register, (byte)data });
	}
	
	public void writeBytesToRegister(int register, byte[] data) throws IOException {
		byte[] bytesToWrite = new byte[data.length + 1];
		bytesToWrite[0] = (byte)register;
		System.arraycopy(data, 0, bytesToWrite, 1, data.length);
	}
	
	public I2cBus getBus() {
		return (I2cBus)super.getBus();
	}
}
