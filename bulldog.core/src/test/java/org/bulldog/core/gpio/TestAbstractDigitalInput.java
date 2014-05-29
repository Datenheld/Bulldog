package org.bulldog.core.gpio;

import junit.framework.TestCase;

import org.bulldog.core.Signal;
import org.bulldog.core.mocks.MockedDigitalInput;
import org.bulldog.core.util.BulldogUtil;
import org.junit.Before;
import org.junit.Test;

public class TestAbstractDigitalInput {

	private Pin pin;
	
	@Before
	public void setup() {
		pin = new Pin("Testpin", 0, "A", 0);
		MockedDigitalInput input = new MockedDigitalInput(pin);
		pin.addFeature(input);
	}
	
	@Test
	public void testRead() {
		MockedDigitalInput input = pin.as(MockedDigitalInput.class);
		input.setSignalToRead(Signal.High);
		TestCase.assertEquals(Signal.High, input.readSignal());
		
		input.setSignalToRead(Signal.Low);
		TestCase.assertEquals(Signal.Low, input.readSignal());
	}
	
	@Test
	public void testReadSignalDebounced() {
		MockedDigitalInput input = pin.as(MockedDigitalInput.class);
		input.bounceSignal(Signal.High, 100);
		BulldogUtil.sleepMs(10);
		long start = System.currentTimeMillis();
		long delta = 0;
		while(delta < 100) {
			TestCase.assertEquals(Signal.High, input.readSignalDebounced(100));
			delta = System.currentTimeMillis() - start;
		}
		
		input.bounceSignal(Signal.High, 20);
		BulldogUtil.sleepMs(5);
		while(delta < 100) {
			TestCase.assertEquals(Signal.High, input.readSignalDebounced(30));
			delta = System.currentTimeMillis() - start;
		}
		
		input.bounceSignal(Signal.High, 5);
		BulldogUtil.sleepMs(5);
		while(delta < 100) {
			TestCase.assertEquals(Signal.High, input.readSignalDebounced(100));
			delta = System.currentTimeMillis() - start;
		}
	}
	
	@Test
	public void testInterrupts() {
		
	}
	
	@Test
	public void testInterruptDebounce() {
		
	}
	
	@Test
	public void testName() {

	}
	
}
