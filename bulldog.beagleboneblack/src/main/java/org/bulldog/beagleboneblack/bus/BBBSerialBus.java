package org.bulldog.beagleboneblack.bus;

import java.io.IOException;

import org.bulldog.core.bus.BusConnection;
import org.bulldog.core.bus.SerialBus;

public class BBBSerialBus implements SerialBus {

	private String deviceFilePath;
	
	public BBBSerialBus(String filename) {
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
		BBBSerialBus other = (BBBSerialBus) obj;
		if (deviceFilePath == null) {
			if (other.deviceFilePath != null)
				return false;
		} else if (!deviceFilePath.equals(other.deviceFilePath))
			return false;
		return true;
	}

}
