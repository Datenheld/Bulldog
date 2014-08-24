package org.bulldog.core.platform;

import junit.framework.TestCase;

import org.bulldog.core.io.IOPort;
import org.bulldog.core.io.bus.i2c.I2cBus;
import org.bulldog.core.io.serial.SerialPort;
import org.bulldog.core.mocks.MockedBoard;
import org.bulldog.core.mocks.MockedPinFeature1;
import org.junit.Test;

public class TestAbstractBoard {

	private String[] aliases = new String[] { "HanSolo" , "Princess Leia", "Chewbacca", "R2D2", "C3PO", "Luke" };

	
	@Test
	public void testGetBusByAlias() {
		MockedBoard board = new MockedBoard();
		
		for(int i = 0; i < aliases.length; i++) {
			if(i%3 == 0)  {
				board.getI2cBuses().get(i).setAlias(aliases[i]);
			} else if(i%3 == 1) {
				board.getSerialPorts().get(i).setAlias(aliases[i]);
			} else {
				board.getSpiBuses().get(i).setAlias(aliases[i]);
			}
		}
		
		IOPort availablePort = board.getIOPortByAlias(aliases[4]);
		TestCase.assertNotNull(availablePort);
		TestCase.assertEquals(aliases[4], availablePort.getAlias());
		TestCase.assertTrue(SerialPort.class.isAssignableFrom(availablePort.getClass()));
		
		
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
	
	@Test
	public void testGetSpiBus() {
		MockedBoard board = new MockedBoard();
		TestCase.assertNotNull(board.getSpiBus("SPI1"));
		TestCase.assertNull(board.getSpiBus("SPI999"));
	}
	
	@Test
	public void testProperties() {
		MockedBoard board = new MockedBoard();
		TestCase.assertNotNull(board.getProperties());
		TestCase.assertNull(board.getProperty("ASD"));
		
		board.setProperty("test", "hello");
		try {
			board.setProperty("test2", null);
			TestCase.fail();
		} catch(NullPointerException ex) { }
		TestCase.assertTrue(board.hasProperty("test"));
		TestCase.assertFalse(board.hasProperty("test2"));
		TestCase.assertFalse(board.hasProperty("TEST"));
		TestCase.assertFalse(board.hasProperty("test2 "));
		try {
			TestCase.assertFalse(board.hasProperty(null));
			TestCase.fail();
		} catch(NullPointerException ex) {}
		
		TestCase.assertEquals("hello", board.getProperty("test"));
	}
	
	@Test
	public void testCleanup() {
		MockedBoard board = new MockedBoard();
		
		MockedPinFeature1 feature1 = board.getPin("P1").as(MockedPinFeature1.class);
		MockedPinFeature1 feature2 = board.getPin("P2").as(MockedPinFeature1.class);
		TestCase.assertTrue(feature1.isSetup());
		TestCase.assertTrue(feature2.isSetup());
		
		board.cleanup();
		
		TestCase.assertFalse(feature1.isSetup());
		TestCase.assertFalse(feature2.isSetup());
	}
}
