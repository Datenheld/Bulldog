package org.bulldog.core.gpio.base;

import junit.framework.TestCase;

import org.bulldog.core.gpio.DigitalInput;
import org.bulldog.core.gpio.Pin;
import org.bulldog.core.mocks.MockedDigitalInput;
import org.junit.Before;
import org.junit.Test;

public class TestAbstractDigitalInput {

	private Pin pin;
	private MockedDigitalInput mock;
	private GpioTester gpioTester;
	
	@Before
	public void setup() {
		pin = new Pin("Testpin", 0, "A", 0);
		mock = new MockedDigitalInput(pin);
		pin.addFeature(mock);
		gpioTester = new GpioTester();
	}
	
	@Test
	public void testRead() {
		gpioTester.testRead(pin.as(DigitalInput.class), mock);
	}
	
	@Test
	public void testReadDebounced() {
		gpioTester.testReadDebounced(pin.as(DigitalInput.class), mock);
	}
	
	@Test
	public void testInterruptTrigger() {
		gpioTester.testInterruptTrigger(pin.as(DigitalInput.class));
	}
	
	@Test
	public void testInterrupts() {
		gpioTester.testInterrupts(pin.as(DigitalInput.class));
	}
	
	@Test
	public void testName() {
		DigitalInput input = pin.as(DigitalInput.class);
		TestCase.assertNotNull(input.getName());
	}
	
}
