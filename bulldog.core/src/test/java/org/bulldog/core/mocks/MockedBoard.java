package org.bulldog.core.mocks;

import org.bulldog.core.AbstractBoard;
import org.bulldog.core.gpio.Pin;

public class MockedBoard extends AbstractBoard {

	public MockedBoard() {
		for(int i = 0; i < 10; i++) {
			getPins().add(new Pin("P" + i, i));
			getI2cBuses().add(new MockedI2cBus("I2C" + i));
			getSerialBuses().add(new MockedSerialBus("Serial" + i));
		}
	}
	
	@Override
	public String getName() {
		return "MockedBoard";
	}

}
