package org.bulldog.linux.io;

import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.bulldog.core.gpio.Pin;
import org.bulldog.core.io.bus.BusConnection;
import org.bulldog.core.io.bus.i2c.I2cBus;
import org.bulldog.core.util.BulldogUtil;
import org.bulldog.linux.jni.NativeI2c;
import org.bulldog.linux.jni.NativeTools;

public class LinuxI2cBus implements I2cBus {
	
	private static final String ERROR_OPENING_BUS = "Bus '%s' could not be opened";
	private static final String ERROR_CLOSING_BUS = "Bus could not be closed. Invalid file descriptor?";
	private static final String ERROR_SELECTING_SLAVE = "Error selecting slave on address %s";
	private static final String ERROR_WRITING_BYTE = "Byte could not be written to bus";
	private static final String ERROR_READING_BYTE = "Byte could not be read from bus";
	
	private String deviceFilePath;
	private boolean isOpen = false;
	private int fileDescriptor;
	private int selectedAddress;
	private String alias;
	private String name;
	private FileInputStream inputStream;
	private FileOutputStream outputStream;
	private FileDescriptor streamDescriptor;
	
	public LinuxI2cBus(String name, String deviceFilePath) {
		this.deviceFilePath = deviceFilePath;
		this.name = name;
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
			finalizeStreams();
		}
	}
	
	public BusConnection createConnection(int address) {
		return new BusConnection(this, address);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LinuxI2cBus other = (LinuxI2cBus) obj;
		if (deviceFilePath == null) {
			if (other.deviceFilePath != null)
				return false;
		} else if (!deviceFilePath.equals(other.deviceFilePath))
			return false;
		return true;
	}

	public String getAlias() {
		return alias;
	}
	
	@Override
	public int getFrequency() {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public FileInputStream getInputStream() throws IOException {
		return inputStream;
	}
	

	public String getName() {
		return name;
	}
	

	@Override
	public FileOutputStream getOutputStream() throws IOException {
		return outputStream;
	}
	
	public Pin getSCL() {
		throw new UnsupportedOperationException();
	}

	public Pin getSDA() {
		throw new UnsupportedOperationException();
	}

	public int getSelectedAddress() {
		return selectedAddress;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((deviceFilePath == null) ? 0 : deviceFilePath
						.hashCode());
		return result;
	}

	public boolean isOpen() {
		return isOpen;
	}

	public void open() throws IOException {
		if(isOpen()) { return; }
		fileDescriptor = NativeI2c.i2cOpen(getDeviceFilePath());
		if(fileDescriptor <= 0) {
			isOpen = false;
			throw new IOException(String.format(ERROR_OPENING_BUS, getDeviceFilePath()));
		} 
		
		streamDescriptor = NativeTools.getJavaDescriptor(fileDescriptor);
		inputStream = new FileInputStream(streamDescriptor);
		outputStream = new FileOutputStream(streamDescriptor);
		isOpen = true;
	}
	
	public byte readByte() throws IOException {
		try {
			return NativeI2c.i2cRead(getFileDescriptor());
		} catch(Exception ex) {
			throw new IOException(ERROR_READING_BYTE);
		}
	}

	@Override
	public int readBytes(byte[] buffer) throws IOException {
		return getInputStream().read(buffer);
	}

	@Override
	public String readString() throws IOException {
		return BulldogUtil.convertStreamToString(getInputStream());
	}

	public void selectAddress(int address) throws IOException {
		selectSlave(address);
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public void writeByte(byte b) throws IOException {
		int returnValue = NativeI2c.i2cWrite(getFileDescriptor(), b);
		if(returnValue < 0) {
			throw new IOException(ERROR_WRITING_BYTE);
		}
	}
	
	
	@Override
	public void writeBytes(byte[] bytes) throws IOException {
		getOutputStream().write(bytes);
	}

	@Override
	public void writeString(String string) throws IOException {
		writeBytes(string.getBytes());
		
	}

	private void finalizeStreams() throws IOException {
		if(inputStream != null) {
			try {
				inputStream.close();
			} catch(Exception ex) {}  
			finally { inputStream = null; }
		}
		
		if(outputStream != null) {
			try {
				outputStream.close();
			} catch(Exception ex) {}
			finally { outputStream = null; }
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
	

	protected String getDeviceFilePath() {
		return this.deviceFilePath;
	}

	protected int getFileDescriptor() {
		return this.fileDescriptor;
	}

}
