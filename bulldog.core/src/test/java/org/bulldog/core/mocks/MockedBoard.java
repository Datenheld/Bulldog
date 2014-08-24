package org.bulldog.core.mocks;

import org.bulldog.core.pinfeatures.Pin;
import org.bulldog.core.platform.AbstractBoard;

public class MockedBoard extends AbstractBoard {

	public MockedBoard() {
		for(int i = 0; i < 10; i++) {
			String pinName = "P" + i;
			Pin pin = new Pin(pinName, i, "A", i);
			pin.addFeature(new MockedPinFeature1(pin));
			getPins().add(pin);
			getI2cBuses().add(new MockedI2cBus("I2C" + i));
			getSerialPorts().add(new MockedSerialPort("Serial" + i));
			getSpiBuses().add(new MockedSpiBus("SPI" + i));
		}
	}
	
	@Override
	public String getName() {
		return "MockedBoard";
	}


}
