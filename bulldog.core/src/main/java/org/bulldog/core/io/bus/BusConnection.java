package org.bulldog.core.io.bus;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class BusConnection {

	private int address;
	private Bus bus;
	
	public BusConnection(Bus bus, int address) {
		this.bus = bus;
		this.address = address;
	}
	
	public Bus getBus() {
		return bus;
	}
	
	public int getAddress() {
		return address;
	}
	
	public void writeByte(int b) throws IOException {
		acquireBus();
		bus.writeByte(b);
	}
	
	public void writeBytes(byte[] bytes) throws IOException {
		acquireBus();
		bus.writeBytes(bytes);
	}
	
	public void writeString(String string) throws IOException  {
		acquireBus();
		bus.writeString(string);
	}
	
	public byte readByte() throws IOException {
		acquireBus();
		return bus.readByte();
	}
	
	public int readBytes(byte[] buffer) throws IOException {
		acquireBus();
		return bus.readBytes(buffer);
	}
	
	public String readString() throws IOException {
		acquireBus();
		return bus.readString();
	}
	
	protected void acquireBus() throws IOException {
		if(!getBus().isOpen()) {
			getBus().open();
		}
		
		if(!getBus().isSlaveSelected(address)) {
			getBus().selectSlave(address);
		}
	}
	
	public OutputStream getOutputStream() throws IOException {
		acquireBus();
		return bus.getOutputStream();
	}
	
	public InputStream getInputStream() throws IOException {
		acquireBus();
		return bus.getInputStream();
	}
}
