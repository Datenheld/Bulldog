package org.bulldog.core.io.bus.i2c;

import java.io.IOException;

import org.bulldog.core.io.bus.BusDevice;

public class I2cDevice extends BusDevice {

	public I2cDevice(I2cConnection connection) {
		super(connection);
	}
	
	public I2cDevice(I2cBus bus, int address) {
		this(bus.createI2cConnection(address));
	}
	
	public byte readByteFromRegister(int register) throws IOException {
		return getBusConnection().readByteFromRegister(register);
	}
	
	public int readBytesFromRegister(int register, byte[] buffer) throws IOException {
		return getBusConnection().readBytesFromRegister(register, buffer);
	}
	
	public void writeByteToRegister(int register, int data) throws IOException {
		getBusConnection().writeByteToRegister(register, data);
	}
	
	public void writeBytesToRegister(int register, byte[] data) throws IOException {
		getBusConnection().writeBytesToRegister(register, data);
	}
	
	public I2cConnection getBusConnection() {
		return (I2cConnection)super.getBusConnection();
	}
}
