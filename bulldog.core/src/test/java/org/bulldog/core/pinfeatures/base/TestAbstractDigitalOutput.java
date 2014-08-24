package org.bulldog.core.pinfeatures.base;

import junit.framework.TestCase;

import org.bulldog.core.mocks.MockedDigitalOutput;
import org.bulldog.core.pinfeatures.DigitalOutput;
import org.bulldog.core.pinfeatures.Pin;
import org.junit.Before;
import org.junit.Test;

public class TestAbstractDigitalOutput {

	private Pin pin;
	private GpioTester gpioTester;
	
	@Before
	public void setup() {
		pin = new Pin("Testpin", 0, "A", 0);
		MockedDigitalOutput output = new MockedDigitalOutput(pin);
		pin.addFeature(output);
		gpioTester = new GpioTester();
	}
	
	@Test
	public void testOutput() {
		gpioTester.testOutput(pin.as(DigitalOutput.class));
	}
	
	@Test
	public void testName() {
		DigitalOutput output = pin.as(DigitalOutput.class);
		String name = output.getName();
		TestCase.assertNotNull(name);
	}
	
	@Test
	public void testBlinking() {
		gpioTester.testBlinking(pin.as(DigitalOutput.class));
	}


}
