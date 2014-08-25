package org.bulldog.core.mocks;

import org.bulldog.core.gpio.Pin;
import org.bulldog.core.platform.AbstractBoard;

public class MockedBoard extends AbstractBoard {

	public MockedBoard() {
		for(int i = 0; i < 10; i++) {
			getPins().add(new Pin("P" + i, i, "A", i));
			getI2cBuses().add(new MockedI2cBus("I2C" + i));
			getSerialPorts().add(new MockedSerialPort("Serial" + i));
		}
	}
	
	@Override
	public String getName() {
		return "MockedBoard";
	}


}
