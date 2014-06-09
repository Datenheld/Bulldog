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
		
		bus.selectAddress(0x05);
		TestCase.assertEquals(0x05, bus.getSelectedAddress());
		busConnection.readByte();
		TestCase.assertEquals(0x10, bus.getSelectedAddress());
		TestCase.assertTrue(bus.isOpen());
		TestCase.assertEquals(0x10, busConnection.getAddress());
		
		bus.close();
		TestCase.assertFalse(bus.isOpen());
		bus.selectAddress(0x10);
		TestCase.assertEquals(0x10, bus.getSelectedAddress());
		bus.open();
		TestCase.assertTrue(bus.isOpen());
		busConnection.writeByte(0xff);
		TestCase.assertEquals(0x10, bus.getSelectedAddress());
		TestCase.assertTrue(bus.isOpen());
		
		bus.close();
		TestCase.assertFalse(bus.isOpen());
		bus.selectAddress(0x10);
		TestCase.assertEquals(0x10, bus.getSelectedAddress());
		TestCase.assertFalse(bus.isOpen());
		busConnection.getOutputStream();
		TestCase.assertEquals(0x10, bus.getSelectedAddress());
		TestCase.assertTrue(bus.isOpen());
		
		bus.close();
		TestCase.assertFalse(bus.isOpen());
		bus.selectAddress(0x10);
		TestCase.assertEquals(0x10, bus.getSelectedAddress());
		TestCase.assertFalse(bus.isOpen());
		busConnection.getInputStream();
		TestCase.assertEquals(0x10, bus.getSelectedAddress());
		TestCase.assertTrue(bus.isOpen());
	}
	
}
