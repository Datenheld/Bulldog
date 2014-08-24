package org.bulldog.core.io.bus.serial;

import junit.framework.TestCase;

import org.bulldog.core.io.serial.SerialDataEventArgs;
import org.bulldog.core.mocks.MockedSerialPort;
import org.junit.Test;

public class TestSerialDataEventArgs {

	@Test
	public void testSerialDataEventArgs() {
		MockedSerialPort port = new MockedSerialPort("TESTPORT");
		byte[] bytes = "TEST".getBytes();
		SerialDataEventArgs args1 = new SerialDataEventArgs(port, bytes);
		TestCase.assertEquals("TEST", args1.getDataAsString());
		TestCase.assertSame(bytes, args1.getData());
		TestCase.assertSame(port, args1.getPort());
	}
	
}
