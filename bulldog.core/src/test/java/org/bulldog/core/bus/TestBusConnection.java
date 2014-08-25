package org.bulldog.core.bus;

import java.io.IOException;

import junit.framework.TestCase;

import org.bulldog.core.io.bus.BusConnection;
import org.bulldog.core.mocks.MockedBus;
import org.junit.Test;

public class TestBusConnection {

	@Test
	public void testBusConnection() throws IOException {
		MockedBus bus = new MockedBus("TESTBUS");
		BusConnection busConnection = new BusConnection(bus, 0x10);
		TestCase.assertEquals(0x10, busConnection.getAddress());
		
		bus.selectSlave(0x05);
		TestCase.assertTrue(bus.isSlaveSelected(0x05));
		busConnection.readByte();
		TestCase.assertTrue(bus.isSlaveSelected(0x10));
		TestCase.assertTrue(bus.isOpen());
		TestCase.assertEquals(0x10, busConnection.getAddress());
		
		bus.close();
		TestCase.assertFalse(bus.isOpen());
		bus.selectSlave(0x10);
		TestCase.assertTrue(bus.isSlaveSelected(0x10));
		bus.open();
		TestCase.assertTrue(bus.isOpen());
		busConnection.writeByte(0xff);
		TestCase.assertTrue(bus.isSlaveSelected(0x10));
		TestCase.assertTrue(bus.isOpen());
		
		bus.close();
		TestCase.assertFalse(bus.isOpen());
		bus.selectSlave(0x10);
		TestCase.assertTrue(bus.isSlaveSelected(0x10));
		TestCase.assertFalse(bus.isOpen());
		busConnection.getOutputStream();
		TestCase.assertTrue(bus.isSlaveSelected(0x10));
		TestCase.assertTrue(bus.isOpen());
		
		bus.close();
		TestCase.assertFalse(bus.isOpen());
		bus.selectSlave(0x10);
		TestCase.assertTrue(bus.isSlaveSelected(0x10));
		TestCase.assertFalse(bus.isOpen());
		busConnection.getInputStream();
		TestCase.assertTrue(bus.isSlaveSelected(0x10));
		TestCase.assertTrue(bus.isOpen());
	}
	
}
