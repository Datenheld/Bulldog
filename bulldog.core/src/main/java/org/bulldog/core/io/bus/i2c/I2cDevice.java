package org.bulldog.core.io.bus.i2c;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.bulldog.core.io.IOPort;
import org.bulldog.core.io.bus.Bus;
import org.bulldog.core.io.bus.BusConnection;

public class I2cDevice implements IOPort {

	private BusConnection connection;
	private String name;
	private String alias;
	
	public I2cDevice(BusConnection connection) {
		this.connection = connection;
	}
	
	public I2cDevice(Bus bus, int address) {
		this(bus.createConnection(address));
	}
	
	public void writeToRegister(byte register, byte data) throws IOException {
		connection.writeBytes(new byte[] { register, data });
	}

	public void writeToRegister(int register, byte data) throws IOException {
		writeToRegister((byte)register, data);
	}
	
	public void writeToRegister(int register, byte[] data) throws IOException {
		byte[] bytesToWrite = new byte[data.length + 1];
		bytesToWrite[0] = (byte)register;
		System.arraycopy(data, 0, bytesToWrite, 1, data.length);
	}
	
	public void writeByte(byte b) throws IOException {
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
	
	public byte readFromRegister(int register) throws IOException {
		connection.writeByte((byte)register);
		return connection.readByte();
	}
	
	public BusConnection getBusConnection() {
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
