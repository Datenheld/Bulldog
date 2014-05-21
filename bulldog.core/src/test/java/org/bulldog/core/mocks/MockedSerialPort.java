package org.bulldog.core.mocks;

import org.bulldog.core.Parity;
import org.bulldog.core.io.serial.SerialDataListener;
import org.bulldog.core.io.serial.SerialPort;

public class MockedSerialPort extends MockedBus implements SerialPort {
	
	public MockedSerialPort(String name) {
		super(name);
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
	public void setBlocking(boolean blocking) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean getBlocking() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void addListener(SerialDataListener listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeListener(SerialDataListener listener) {
		// TODO Auto-generated method stub
		
	}
	
}
