package org.bulldog.core.mocks;

import java.io.IOException;

import org.bulldog.core.bus.Bus;
import org.bulldog.core.bus.BusConnection;

public class MockedBus implements Bus {

	private String name;
	private String alias;
	
	public MockedBus(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public void open() throws IOException {
		// TODO Auto-generated method stub
		
	}

	public boolean isOpen() {
		// TODO Auto-generated method stub
		return false;
	}

	public void close() throws IOException {
		// TODO Auto-generated method stub
		
	}

	public void writeByte(byte b) throws IOException {
		// TODO Auto-generated method stub
		
	}

	public byte readByte() throws IOException {
		// TODO Auto-generated method stub
		return 0;
	}

	public void selectAddress(int address) throws IOException {
		// TODO Auto-generated method stub
		
	}

	public int getSelectedAddress() {
		// TODO Auto-generated method stub
		return 0;
	}

	public BusConnection createConnection(int address) {
		// TODO Auto-generated method stub
		return null;
	}

	
	
}
