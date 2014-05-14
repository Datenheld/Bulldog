package org.bulldog.core;

import junit.framework.Assert;

import org.bulldog.core.gpio.Pin;
import org.bulldog.core.mocks.MockedBoard;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class TestAbstractBoard {

	@Rule
	public ExpectedException exception = ExpectedException.none();

	private String[] pinAliases = new String[] { "HanSolo" , "Princess Leia", "Chewbacca", "R2D2", "C3PO" };
	
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
	public void testGetPinByAlias() {
		MockedBoard board = new MockedBoard();
		
		for(int i = 0; i < pinAliases.length; i++) {
			board.getPins().get(i).setAlias(pinAliases[i]);
		}
		
		Pin availablePin = board.getPinByAlias(pinAliases[4]);
		Assert.assertNotNull(availablePin);
		Assert.assertEquals(pinAliases[4], availablePin.getAlias());
		
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
	public void testGetI2cBus() {
		
	}
	
	@Test
	public void testGetSerialBus() {
		
	}
	

	
}
