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
		return 0;
	}

	@Override
	public void setBaudRate(int baudRate) {	
	}

	@Override
	public Parity getParity() {
		return null;
	}

	@Override
	public void setParity(Parity parity) {	
	}

	@Override
	public void setBlocking(boolean blocking) {
	}

	@Override
	public boolean getBlocking() {
		return false;
	}

	@Override
	public void addListener(SerialDataListener listener) {	
	}

	@Override
	public void removeListener(SerialDataListener listener) {
	}
	
}
