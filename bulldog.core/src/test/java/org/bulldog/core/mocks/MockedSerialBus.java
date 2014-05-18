package org.bulldog.core.mocks;

import org.bulldog.core.Parity;
import org.bulldog.core.bus.SerialBus;

public class MockedSerialBus extends MockedBus implements SerialBus {
	
	public MockedSerialBus(String name) {
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
	
}
