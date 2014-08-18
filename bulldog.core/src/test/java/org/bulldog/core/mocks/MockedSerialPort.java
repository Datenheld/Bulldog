package org.bulldog.core.mocks;

import org.bulldog.core.Parity;
import org.bulldog.core.io.serial.SerialDataListener;
import org.bulldog.core.io.serial.SerialPort;

public class MockedSerialPort extends MockedIOPort implements SerialPort {
	
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

	@Override
	public int getDataBits() {
		return 0;
	}

	@Override
	public void setDataBits(int dataBits) {
	}

	@Override
	public int getStopBits() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setStopBits(int stopBits) {
		// TODO Auto-generated method stub
		
	}
	
}
