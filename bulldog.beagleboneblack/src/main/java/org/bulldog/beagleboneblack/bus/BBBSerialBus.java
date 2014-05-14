package org.bulldog.beagleboneblack.bus;

import java.io.IOException;

import org.bulldog.core.bus.BusConnection;
import org.bulldog.core.bus.SerialBus;

public class BBBSerialBus implements SerialBus {

	public BBBSerialBus(String filename) {
		
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

}
