package org.bulldog.core;

import junit.framework.TestCase;

import org.bulldog.core.Signal;
import org.junit.Test;


public class TestSignal {

	@Test
	public void testNumericValue() {
		TestCase.assertEquals(1, Signal.High.getNumericValue());
		TestCase.assertEquals(0, Signal.Low.getNumericValue());
		
		TestCase.assertEquals(Signal.Low, Signal.fromNumericValue(0));
		TestCase.assertEquals(Signal.High, Signal.fromNumericValue(1));
		Signal signal = Signal.fromNumericValue(1000);
		TestCase.assertEquals(Signal.High, signal);
		TestCase.assertEquals(1, signal.getNumericValue());
	}
	
	@Test
	public void testBooleanValue() {
		TestCase.assertTrue(Signal.High.getBooleanValue());
		TestCase.assertFalse(Signal.Low.getBooleanValue());
		
		TestCase.assertEquals(Signal.Low, Signal.fromBooleanValue(false));
		TestCase.assertEquals(Signal.High, Signal.fromBooleanValue(true));
	}
	
	@Test 
	public void testInverse() {
		TestCase.assertEquals(Signal.Low, Signal.High.inverse());
		TestCase.assertEquals(Signal.High, Signal.Low.inverse());
	}
	
}
