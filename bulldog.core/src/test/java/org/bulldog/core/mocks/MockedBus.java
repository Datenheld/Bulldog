package org.bulldog.core.mocks;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.bulldog.core.io.bus.Bus;
import org.bulldog.core.io.bus.BusConnection;

public class MockedBus implements Bus {

	private String name;
	private String alias;
	private boolean isOpen = false;
	private int selectedAddress = 0;
	
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
		isOpen = true;
	}

	public boolean isOpen() {
		return isOpen;
	}

	public void close() throws IOException {
		isOpen = false;
	}

	public void writeByte(byte b) throws IOException {
	}

	public byte readByte() throws IOException {
		return 0;
	}

	public void selectAddress(int address) throws IOException {
		selectedAddress = address;
	}

	public int getSelectedAddress() {
		return selectedAddress;
	}

	public BusConnection createConnection(int address) {
		return new BusConnection(this, address);
	}

	@Override
	public FileOutputStream getOutputStream() throws IOException {
		return null;
	}

	@Override
	public FileInputStream getInputStream() throws IOException {
		return null;
	}

	@Override
	public void writeBytes(byte[] bytes) {
	}

	@Override
	public int readBytes(byte[] buffer) {
		return 0;
	}

	@Override
	public void writeString(String string) throws IOException {	
	}

	@Override
	public String readString() throws IOException {
		return null;
	}

}
