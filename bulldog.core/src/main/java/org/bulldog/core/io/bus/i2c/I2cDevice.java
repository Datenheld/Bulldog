package org.bulldog.core.io.bus.i2c;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.bulldog.core.io.IOPort;

public class I2cDevice implements IOPort {

	private I2cConnection connection;
	private String name;
	private String alias;
	
	public I2cDevice(I2cConnection connection) {
		this.connection = connection;
	}
	
	public I2cDevice(I2cBus bus, int address) {
		this(bus.createI2cConnection(address));
	}
	
	public void writeByte(int b) throws IOException {
		getBusConnection().writeByte(b);
	}
	
	public void writeBytes(byte[] bytes) throws IOException {
		getBusConnection().writeBytes(bytes);
	}
	
	public void writeString(String string) throws IOException  {
		getBusConnection().writeString(string);
	}
	
	public byte readByte() throws IOException {
		return getBusConnection().readByte();
	}
	
	public int readBytes(byte[] buffer) throws IOException {
		return getBusConnection().readBytes(buffer);
	}
	
	public String readString() throws IOException {
		return getBusConnection().readString();
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
		return connection;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getAlias() {
		return alias;
	}

	@Override
	public void setAlias(String alias) {
		this.alias = alias;
	}

	@Override
	public void open() throws IOException {
		getBusConnection().getBus().open();
	}

	@Override
	public boolean isOpen() {
		return getBusConnection().getBus().isOpen();
	}

	@Override
	public void close() throws IOException {
		getBusConnection().getBus().close();
	}

	@Override
	public OutputStream getOutputStream() throws IOException {
		return getBusConnection().getOutputStream();
	}

	@Override
	public InputStream getInputStream() throws IOException {
		return getBusConnection().getInputStream();
	}
}
