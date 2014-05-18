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
	
	public void writeByte(byte b) throws IOException {
		acquireBus();
		bus.writeByte(b);
	}
	
	public byte readByte() throws IOException {
		acquireBus();
		return bus.readByte();
	}
	
	private void acquireBus() throws IOException {
		if(!getBus().isOpen()) {
			getBus().open();
		}
		
		if(getBus().getSelectedAddress() != address) {
			getBus().selectAddress(address);
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
