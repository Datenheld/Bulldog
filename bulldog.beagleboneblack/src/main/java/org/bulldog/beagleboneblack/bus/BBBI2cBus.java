package org.bulldog.beagleboneblack.bus;

import java.io.IOException;

import org.bulldog.beagleboneblack.jni.NativeI2c;
import org.bulldog.core.bus.BusConnection;
import org.bulldog.core.bus.I2cBus;
import org.bulldog.core.gpio.Pin;

public class BBBI2cBus implements I2cBus {
	
	private static final String ERROR_OPENING_BUS = "Bus '%s' could not be opened";
	private static final String ERROR_CLOSING_BUS = "Bus could not be closed. Invalid file descriptor?";
	private static final String ERROR_SELECTING_SLAVE = "Error selecting slave on address %s";
	private static final String ERROR_WRITING_BYTE = "Byte could not be written to bus";
	private static final String ERROR_READING_BYTE = "Byte could not be read from bus";
	
	private String busDeviceFilePath;
	private boolean isOpen = false;
	private int fileDescriptor;
	private int selectedAddress;
	private String alias;
	
	public BBBI2cBus(String busDeviceFilePath) {
		this.busDeviceFilePath = busDeviceFilePath;
	}

	public void open() throws IOException {
		if(isOpen()) { return; }
		fileDescriptor = NativeI2c.i2cOpen(getBusDeviceFilePath());
		if(fileDescriptor == 0) {
			isOpen = false;
			throw new IOException(String.format(ERROR_OPENING_BUS, getBusDeviceFilePath()));
		} 
		
		isOpen = true;
	}
	
	public boolean isOpen() {
		return isOpen;
	}

	public void close() throws IOException {
		if(!isOpen()) { return; }
		fileDescriptor = 0;
		isOpen = false;
		int returnValue = NativeI2c.i2cClose(fileDescriptor);
		if(returnValue < 0) {
			throw new IOException(ERROR_CLOSING_BUS);
		}
	}
	
	public void writeByte(byte b) throws IOException {
		int returnValue = NativeI2c.i2cWrite(getFileDescriptor(), b);
		if(returnValue < 0) {
			throw new IOException(ERROR_WRITING_BYTE);
		}
	}
	
	public byte readByte() throws IOException {
		try {
			return NativeI2c.i2cRead(getFileDescriptor());
		} catch(Exception ex) {
			throw new IOException(ERROR_READING_BYTE);
		}
	}
	

	private void selectSlave(int address) throws IOException {
		if(!isOpen()) {
			open();
		}
		
		int returnCode = NativeI2c.i2cSelectSlave(getFileDescriptor(), address);
		if(returnCode < 0) {
			throw new IOException(String.format(ERROR_SELECTING_SLAVE, Integer.toHexString(address)));
		}
		
		this.selectedAddress = address;
	}
	

	protected String getBusDeviceFilePath() {
		return this.busDeviceFilePath;
	}
	
	protected int getFileDescriptor() {
		return this.fileDescriptor;
	}

	public void selectAddress(int address) throws IOException {
		selectSlave(address);
	}

	public int getSelectedAddress() {
		return selectedAddress;
	}

	public BusConnection createConnection(int address) {
		return new BusConnection(this, address);
	}

	public Pin getSDA() {
		// TODO Auto-generated method stub
		return null;
	}

	public Pin getSCL() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getName() {
		return busDeviceFilePath;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}
	
}
