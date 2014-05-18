package org.bulldog.core;

import junit.framework.Assert;

import org.bulldog.core.gpio.Pin;
import org.bulldog.core.io.IOPort;
import org.bulldog.core.io.bus.Bus;
import org.bulldog.core.io.bus.I2cBus;
import org.bulldog.core.mocks.MockedBoard;
import org.junit.Test;

public class TestAbstractBoard {

	private String[] aliases = new String[] { "HanSolo" , "Princess Leia", "Chewbacca", "R2D2", "C3PO" };
	
	@Test
	public void testGetPinByName() {
		MockedBoard board = new MockedBoard();
		
		Pin availablePin = board.getPinByName("P4");
		Assert.assertNotNull(availablePin);
		Assert.assertEquals("P4", availablePin.getName());
		
		Pin nonExistentPin = board.getPinByName("P11");
		Assert.assertNull(nonExistentPin);
		
		nonExistentPin = board.getPinByName("Chewbacca");
		Assert.assertNull(nonExistentPin);
		
		for(Pin pin : board.getPins()) {
			Pin crossCheck = board.getPinByName(pin.getName());
			Assert.assertSame(pin, crossCheck);
		}
		
		try {
			board.getPinByName(null);
			Assert.fail();
		} catch(IllegalArgumentException ex) {}
	}
	
	@Test
	public void testGetPin() {
		MockedBoard board = new MockedBoard();
		
		Pin availablePin = board.getPin("A", 1);
		Assert.assertNotNull(availablePin);
		Assert.assertEquals("A", availablePin.getPort());
		Assert.assertEquals(1, availablePin.getIndexOnPort());
		
		Pin nonExistentPin = board.getPin("A", 11);
		Assert.assertNull(nonExistentPin);
		
		nonExistentPin = board.getPin("Chewbacca", 1);
		Assert.assertNull(nonExistentPin);
		
		for(Pin pin : board.getPins()) {
			Pin crossCheck = board.getPin(pin.getPort(), pin.getIndexOnPort());
			Assert.assertSame(pin, crossCheck);
		}
		
		try {
			board.getPin(null, 2);
			Assert.fail();
		} catch(IllegalArgumentException ex) {}

		try {
			board.getPin(null, -1);
			Assert.fail();
		} catch(IllegalArgumentException ex) {}
		
		try {
			board.getPin("A", -1);
			Assert.fail();
		} catch(IllegalArgumentException ex) {}
	}
	
	
	@Test
	public void testGetPinByAlias() {
		MockedBoard board = new MockedBoard();
		
		for(int i = 0; i < aliases.length; i++) {
			board.getPins().get(i).setAlias(aliases[i]);
		}
		
		Pin availablePin = board.getPinByAlias(aliases[4]);
		Assert.assertNotNull(availablePin);
		Assert.assertEquals(aliases[4], availablePin.getAlias());
		
		Pin nonExistentPin = board.getPinByAlias("LALALALALA");
		Assert.assertNull(nonExistentPin);
		
		nonExistentPin = board.getPinByAlias("Greedo");
		Assert.assertNull(nonExistentPin);
		
		for(Pin pin : board.getPins()) {
			if(pin.getAlias() != null) {
				Pin crossCheck = board.getPinByAlias(pin.getAlias());
				Assert.assertSame(pin, crossCheck);
			} else {
				try {
					board.getPinByAlias(pin.getAlias());
					Assert.fail();
				} catch(IllegalArgumentException ex) {}
			}
		}
		
		try {
			board.getPinByAlias(null);
			Assert.fail();
		} catch(IllegalArgumentException ex) {}
		
	}
	
	@Test
	public void testGetPinByAdress() {
		MockedBoard board = new MockedBoard();
		
		Pin availablePin = board.getPinByAddress(0);
		Assert.assertNotNull(availablePin);
		Assert.assertEquals(0, availablePin.getAddress());
		
		Pin nonExistentPin = board.getPinByAddress(-1);
		Assert.assertNull(nonExistentPin);
		
		nonExistentPin = board.getPinByAddress(11);
		Assert.assertNull(nonExistentPin);
		
		for(Pin pin : board.getPins()) {
			Pin crossCheck = board.getPinByAddress(pin.getAddress());
			Assert.assertSame(pin, crossCheck);
		}
	}
	
	
	@Test
	public void testGetBusByAlias() {
		MockedBoard board = new MockedBoard();
		
		for(int i = 0; i < aliases.length; i++) {
			if(i%2 == 0)  {
				board.getI2cBuses().get(i).setAlias(aliases[i]);
			} else {
				board.getSerialPorts().get(i).setAlias(aliases[i]);
			}
		}
		
		IOPort availablePort = board.getIOPortByAlias(aliases[4]);
		Assert.assertNotNull(availablePort);
		Assert.assertEquals(aliases[4], availablePort.getAlias());
		Assert.assertTrue( I2cBus.class.isAssignableFrom(availablePort.getClass()));
		
		
		IOPort nonExistentPort = board.getIOPortByAlias("LALALALALA");
		Assert.assertNull(nonExistentPort);
		
		nonExistentPort = board.getIOPortByAlias("Greedo");
		Assert.assertNull(nonExistentPort);
		
		for(IOPort port : board.getAllIOPorts()) {
			if(port.getAlias() != null) {
				IOPort crossCheck = board.getIOPortByAlias(port.getAlias());
				Assert.assertSame(port, crossCheck);
			} else {
				try {
					board.getIOPortByAlias(port.getAlias());
					Assert.fail();
				} catch(IllegalArgumentException ex) {}

			}
		}
		
		try {
			board.getIOPortByAlias(null);
			Assert.fail();
		} catch(IllegalArgumentException ex) {}
	}
	
	@Test
	public void testGetBusByName() {
		MockedBoard board = new MockedBoard();
		
		IOPort availablePort = board.getIOPortByName("I2C1");
		Assert.assertNotNull(availablePort);
		Assert.assertTrue(availablePort instanceof I2cBus);
		
		IOPort nonExistentPort = board.getIOPortByName("LALALALALA");
		Assert.assertNull(nonExistentPort);
		
		nonExistentPort = board.getIOPortByName("Greedo");
		Assert.assertNull(nonExistentPort);
		
		for(IOPort bus : board.getAllIOPorts()) {
			if(bus.getName() != null) {
				IOPort crossCheck = board.getIOPortByName(bus.getName());
				Assert.assertSame(bus, crossCheck);
			} else {
				try {
					board.getIOPortByName(bus.getName());
					Assert.fail();
				} catch(IllegalArgumentException ex) {}
			}
		}
		
		try {
			board.getIOPortByName(null);
			Assert.fail();
		} catch(IllegalArgumentException ex) {}

	}
}
