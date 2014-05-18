package org.bulldog.beagleboneblack.io;

import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.bulldog.beagleboneblack.jni.NativeI2c;
import org.bulldog.core.gpio.Pin;
import org.bulldog.core.io.bus.BusConnection;
import org.bulldog.core.io.bus.I2cBus;

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
	private FileInputStream inputStream;
	private FileOutputStream outputStream;
	private FileDescriptor streamDescriptor;
	
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
		
		streamDescriptor = NativeI2c.i2cGetJavaDescriptor(fileDescriptor);
		inputStream = new FileInputStream(streamDescriptor);
		outputStream = new FileOutputStream(streamDescriptor);
		isOpen = true;
	}
	
	public boolean isOpen() {
		return isOpen;
	}

	public void close() throws IOException {
		try {
			if(!isOpen()) { return; }
			fileDescriptor = 0;
			isOpen = false;
			int returnValue = NativeI2c.i2cClose(fileDescriptor);
			if(returnValue < 0) {
				throw new IOException(ERROR_CLOSING_BUS);
			}
		} finally {
			inputStream.close();
			inputStream = null;
			outputStream.close();
			outputStream = null;
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
		throw new UnsupportedOperationException();
	}

	public Pin getSCL() {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public int getFrequency() {
		throw new UnsupportedOperationException();
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

	@Override
	public FileOutputStream getOutputStream() throws IOException {
		return outputStream;
	}

	@Override
	public FileInputStream getInputStream() throws IOException {
		return inputStream;
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((busDeviceFilePath == null) ? 0 : busDeviceFilePath
						.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BBBI2cBus other = (BBBI2cBus) obj;
		if (busDeviceFilePath == null) {
			if (other.busDeviceFilePath != null)
				return false;
		} else if (!busDeviceFilePath.equals(other.busDeviceFilePath))
			return false;
		return true;
	}
}
