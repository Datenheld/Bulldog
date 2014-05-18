package org.bulldog.beagleboneblack.io;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.bulldog.core.Parity;
import org.bulldog.core.io.SerialIO;
import org.bulldog.core.io.bus.BusConnection;

public class BBBSerialPort implements SerialIO {

	private String deviceFilePath;
	
	public BBBSerialPort(String filename) {
		this.deviceFilePath = filename;
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

	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getAlias() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setAlias(String alias) {
		// TODO Auto-generated method stub
		
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

	@Override
	public int getBaudRate() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setBaudRate(int baudRate) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Parity getParity() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setParity(Parity parity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public FileOutputStream getOutputStream() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FileInputStream getInputStream() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

}
