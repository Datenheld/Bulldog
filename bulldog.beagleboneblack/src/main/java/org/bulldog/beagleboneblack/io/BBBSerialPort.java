package org.bulldog.beagleboneblack.io;

import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;

import org.bulldog.beagleboneblack.jni.NativeSerial;
import org.bulldog.beagleboneblack.jni.NativeTools;
import org.bulldog.core.Parity;
import org.bulldog.core.io.SerialPort;

public class BBBSerialPort implements SerialPort {

	private static final String ERROR_CLOSING_PORT = "Port could not be closed. Invalid file descriptor?";
	
	private static final int DEFAULT_BAUD_RATE = 9600;
	private static final int DEFAULT_READ_TIMEOUT = 5;
	
	private String deviceFilePath;
	private int baudRate = DEFAULT_BAUD_RATE;
	private boolean isOpen = false;
	private int fileDescriptor = 0;
	private String alias = "";
	private Parity parity = Parity.None;
	private ByteBuffer buffer;
	private FileDescriptor streamDescriptor;
	private OutputStream outputStream;
	private InputStream inputStream;
	
	public BBBSerialPort(String filename) {
		this.deviceFilePath = filename;
	}
	
	private int getParityCode() {
		if(parity == Parity.Even) {
			return NativeSerial.PARENB;
		} else if(parity == Parity.Odd) {
			return NativeSerial.PARENB | NativeSerial.PARODD;
		} else if(parity == Parity.Mark) {
			return NativeSerial.PARENB | NativeSerial.PARODD | NativeSerial.CMSPAR;
		} else if(parity == Parity.Space) {
			return NativeSerial.PARENB | NativeSerial.CMSPAR;
		}
		
		return 0;
	}
	
	public void open() throws IOException {
		fileDescriptor = NativeSerial.serialOpen(deviceFilePath, baudRate, getParityCode(), false, DEFAULT_READ_TIMEOUT);
		streamDescriptor = NativeTools.getJavaDescriptor(fileDescriptor);
		outputStream = new FileOutputStream(streamDescriptor);
		inputStream = new FileInputStream(streamDescriptor);
		isOpen = true;
	}

	public boolean isOpen() {
		return isOpen;
	}

	public void close() throws IOException {
		if(!isOpen()) {
			return;
		}
		
		try {
			int returnValue = NativeSerial.serialClose(fileDescriptor);
			if(returnValue < 0) {
				throw new IOException(ERROR_CLOSING_PORT);
			}
		} finally {
			finalizeStreams();
		}
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
				outputStream = null;
			} catch(Exception ex) {}
			finally { outputStream = null; }
		}
	}

	public void writeByte(byte data) throws IOException {
		NativeSerial.serialWrite(fileDescriptor, data);
	}

	public byte readByte() throws IOException {
		return NativeSerial.serialRead(fileDescriptor);
	}
	
	@Override
	public void writeBytes(byte[] bytes) throws IOException {
		outputStream.write(bytes);
	}

	@Override
	public int readBytes(byte[] buffer) throws IOException {
		return inputStream.read(buffer);
	}

	public String getName() {
		return deviceFilePath;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}
	
	@Override
	public int getBaudRate() {
		return this.baudRate;
	}

	@Override
	public void setBaudRate(int baudRate) {
		this.baudRate = baudRate;
	}

	@Override
	public Parity getParity() {
		return this.parity;
	}

	@Override
	public void setParity(Parity parity) {
		this.parity = parity;
	}

	@Override
	public OutputStream getOutputStream() throws IOException {
		return outputStream;
	}

	@Override
	public InputStream getInputStream() throws IOException {
		return inputStream;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((deviceFilePath == null) ? 0 : deviceFilePath.hashCode());
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
		BBBSerialPort other = (BBBSerialPort) obj;
		if (deviceFilePath == null) {
			if (other.deviceFilePath != null)
				return false;
		} else if (!deviceFilePath.equals(other.deviceFilePath))
			return false;
		return true;
	}

}
