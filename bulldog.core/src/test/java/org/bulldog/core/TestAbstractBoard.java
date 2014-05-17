package org.bulldog.core;

import junit.framework.Assert;

import org.bulldog.core.bus.Bus;
import org.bulldog.core.bus.I2cBus;
import org.bulldog.core.gpio.Pin;
import org.bulldog.core.mocks.MockedBoard;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class TestAbstractBoard {

	@Rule
	public ExpectedException exception = ExpectedException.none();

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
		
		exception.expect(IllegalArgumentException.class);
		board.getPinByName(null);

		exception.expect(IllegalArgumentException.class);
		board.getPinByName("");
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
		
		exception.expect(IllegalArgumentException.class);
		board.getPin(null, 1);

		exception.expect(IllegalArgumentException.class);
		board.getPin(null, -1);
		
		exception.expect(IllegalArgumentException.class);
		board.getPin("A", -1);
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
			if(pin.getAlias() != null && !"".equals(pin.getAlias())) {
				Pin crossCheck = board.getPinByAlias(pin.getAlias());
				Assert.assertSame(pin, crossCheck);
			} else {
				exception.expect(IllegalArgumentException.class);
				board.getPinByAlias(pin.getAlias());
			}
		}
		
		exception.expect(IllegalArgumentException.class);
		board.getPinByAlias(null);

		exception.expect(IllegalArgumentException.class);
		board.getPinByAlias("");
		
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
				board.getSerialBuses().get(i).setAlias(aliases[i]);
			}
		}
		
		Bus availableBus = board.getBusByAlias(aliases[4]);
		Assert.assertNotNull(availableBus);
		Assert.assertEquals(aliases[4], availableBus.getAlias());
		Assert.assertTrue( I2cBus.class.isAssignableFrom(availableBus.getClass()));
		
		
		Bus nonExistentBus = board.getBusByAlias("LALALALALA");
		Assert.assertNull(nonExistentBus);
		
		nonExistentBus = board.getBusByAlias("Greedo");
		Assert.assertNull(nonExistentBus);
		
		for(Bus bus : board.getAllBuses()) {
			if(bus.getAlias() != null && !"".equals(bus.getAlias())) {
				Bus crossCheck = board.getBusByAlias(bus.getAlias());
				Assert.assertSame(bus, crossCheck);
			} else {
				exception.expect(IllegalArgumentException.class);
				board.getBusByAlias(bus.getAlias());
			}
		}
		
		exception.expect(IllegalArgumentException.class);
		board.getBusByAlias(null);

		exception.expect(IllegalArgumentException.class);
		board.getBusByAlias("");
	}
	
	@Test
	public void testGetBusByName() {
		MockedBoard board = new MockedBoard();
		
		Bus availableBus = board.getBusByName("I2C1");
		Assert.assertNotNull(availableBus);
		Assert.assertTrue(availableBus instanceof I2cBus);
		
		Bus nonExistentBus = board.getBusByName("LALALALALA");
		Assert.assertNull(nonExistentBus);
		
		nonExistentBus = board.getBusByName("Greedo");
		Assert.assertNull(nonExistentBus);
		
		for(Bus bus : board.getAllBuses()) {
			if(bus.getName() != null && !"".equals(bus.getName())) {
				Bus crossCheck = board.getBusByName(bus.getName());
				Assert.assertSame(bus, crossCheck);
			} else {
				exception.expect(IllegalArgumentException.class);
				board.getBusByName(bus.getName());
			}
		}
		
		exception.expect(IllegalArgumentException.class);
		board.getBusByName(null);

		exception.expect(IllegalArgumentException.class);
		board.getBusByName("");
	}
}
