package org.bulldog.core.platform;

import junit.framework.TestCase;

import org.bulldog.core.gpio.Pin;
import org.bulldog.core.io.IOPort;
import org.bulldog.core.io.bus.i2c.I2cBus;
import org.bulldog.core.mocks.MockedBoard;
import org.junit.Test;

public class TestAbstractBoard {

	private String[] aliases = new String[] { "HanSolo" , "Princess Leia", "Chewbacca", "R2D2", "C3PO" };
	
	@Test
	public void testGetPinByName() {
		MockedBoard board = new MockedBoard();
		
		Pin availablePin = board.getPin("P4");
		TestCase.assertNotNull(availablePin);
		TestCase.assertEquals("P4", availablePin.getName());
		
		Pin nonExistentPin = board.getPin("P11");
		TestCase.assertNull(nonExistentPin);
		
		nonExistentPin = board.getPin("Chewbacca");
		TestCase.assertNull(nonExistentPin);
		
		for(Pin pin : board.getPins()) {
			Pin crossCheck = board.getPin(pin.getName());
			TestCase.assertSame(pin, crossCheck);
		}
		
		try {
			board.getPin(null);
			TestCase.fail();
		} catch(IllegalArgumentException ex) {}
	}
	
	@Test
	public void testGetPin() {
		MockedBoard board = new MockedBoard();
		
		Pin availablePin = board.getPin("A", 1);
		TestCase.assertNotNull(availablePin);
		TestCase.assertEquals("A", availablePin.getPort());
		TestCase.assertEquals(1, availablePin.getIndexOnPort());
		
		Pin nonExistentPin = board.getPin("A", 11);
		TestCase.assertNull(nonExistentPin);
		
		nonExistentPin = board.getPin("Chewbacca", 1);
		TestCase.assertNull(nonExistentPin);
		
		for(Pin pin : board.getPins()) {
			Pin crossCheck = board.getPin(pin.getPort(), pin.getIndexOnPort());
			TestCase.assertSame(pin, crossCheck);
		}
		
		try {
			board.getPin(null, 2);
			TestCase.fail();
		} catch(IllegalArgumentException ex) {}

		try {
			board.getPin(null, -1);
			TestCase.fail();
		} catch(IllegalArgumentException ex) {}
		
		try {
			board.getPin("A", -1);
			TestCase.fail();
		} catch(IllegalArgumentException ex) {}
	}
	
	
	@Test
	public void testGetPinByAlias() {
		MockedBoard board = new MockedBoard();
		
		for(int i = 0; i < aliases.length; i++) {
			board.getPins().get(i).setAlias(aliases[i]);
		}
		
		Pin availablePin = board.getPinByAlias(aliases[4]);
		TestCase.assertNotNull(availablePin);
		TestCase.assertEquals(aliases[4], availablePin.getAlias());
		
		Pin nonExistentPin = board.getPinByAlias("LALALALALA");
		TestCase.assertNull(nonExistentPin);
		
		nonExistentPin = board.getPinByAlias("Greedo");
		TestCase.assertNull(nonExistentPin);
		
		for(Pin pin : board.getPins()) {
			if(pin.getAlias() != null) {
				Pin crossCheck = board.getPinByAlias(pin.getAlias());
				TestCase.assertSame(pin, crossCheck);
			} else {
				try {
					board.getPinByAlias(pin.getAlias());
					TestCase.fail();
				} catch(IllegalArgumentException ex) {}
			}
		}
		
		try {
			board.getPinByAlias(null);
			TestCase.fail();
		} catch(IllegalArgumentException ex) {}
		
	}
	
	@Test
	public void testGetPinByAdress() {
		MockedBoard board = new MockedBoard();
		
		Pin availablePin = board.getPin(0);
		TestCase.assertNotNull(availablePin);
		TestCase.assertEquals(0, availablePin.getAddress());
		
		Pin nonExistentPin = board.getPin(-1);
		TestCase.assertNull(nonExistentPin);
		
		nonExistentPin = board.getPin(11);
		TestCase.assertNull(nonExistentPin);
		
		for(Pin pin : board.getPins()) {
			Pin crossCheck = board.getPin(pin.getAddress());
			TestCase.assertSame(pin, crossCheck);
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
		TestCase.assertNotNull(availablePort);
		TestCase.assertEquals(aliases[4], availablePort.getAlias());
		TestCase.assertTrue( I2cBus.class.isAssignableFrom(availablePort.getClass()));
		
		
		IOPort nonExistentPort = board.getIOPortByAlias("LALALALALA");
		TestCase.assertNull(nonExistentPort);
		
		nonExistentPort = board.getIOPortByAlias("Greedo");
		TestCase.assertNull(nonExistentPort);
		
		for(IOPort port : board.getAllIOPorts()) {
			if(port.getAlias() != null) {
				IOPort crossCheck = board.getIOPortByAlias(port.getAlias());
				TestCase.assertSame(port, crossCheck);
			} else {
				try {
					board.getIOPortByAlias(port.getAlias());
					TestCase.fail();
				} catch(IllegalArgumentException ex) {}

			}
		}
		
		try {
			board.getIOPortByAlias(null);
			TestCase.fail();
		} catch(IllegalArgumentException ex) {}
	}
	
	@Test
	public void testGetBusByName() {
		MockedBoard board = new MockedBoard();
		
		IOPort availablePort = board.getIOPortByName("I2C1");
		TestCase.assertNotNull(availablePort);
		TestCase.assertTrue(availablePort instanceof I2cBus);
		
		IOPort nonExistentPort = board.getIOPortByName("LALALALALA");
		TestCase.assertNull(nonExistentPort);
		
		nonExistentPort = board.getIOPortByName("Greedo");
		TestCase.assertNull(nonExistentPort);
		
		for(IOPort bus : board.getAllIOPorts()) {
			if(bus.getName() != null) {
				IOPort crossCheck = board.getIOPortByName(bus.getName());
				TestCase.assertSame(bus, crossCheck);
			} else {
				try {
					board.getIOPortByName(bus.getName());
					TestCase.fail();
				} catch(IllegalArgumentException ex) {}
			}
		}
		
		try {
			board.getIOPortByName(null);
			TestCase.fail();
		} catch(IllegalArgumentException ex) {}

	}
	
	@Test
	public void testGetI2cBus() {
		MockedBoard board = new MockedBoard();
		TestCase.assertNotNull(board.getI2cBus("I2C1"));
		TestCase.assertNull(board.getI2cBus("I2C999"));
	}
	
	@Test
	public void testGetSerialPort() {
		MockedBoard board = new MockedBoard();
		TestCase.assertNotNull(board.getSerialPort("Serial1"));
		TestCase.assertNull(board.getI2cBus("Serial999"));
	}
}
