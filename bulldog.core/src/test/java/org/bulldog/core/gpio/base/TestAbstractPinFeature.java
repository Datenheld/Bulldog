package org.bulldog.core.gpio.base;

import junit.framework.TestCase;

import org.bulldog.core.gpio.DigitalOutput;
import org.bulldog.core.gpio.Pin;
import org.bulldog.core.mocks.MockedDigitalOutput;
import org.bulldog.core.mocks.MockedPinFeature1;
import org.junit.Test;

public class TestAbstractPinFeature {

	@Test
	public void testAbstractPinFeature() {
		Pin pin = new Pin("Testpin", 0, "A", 0);
		MockedDigitalOutput output = new MockedDigitalOutput(pin);
		pin.addFeature(output);
		MockedPinFeature1 feature1 = new MockedPinFeature1(pin);
		pin.addFeature(feature1);
		
		TestCase.assertEquals(pin, output.getPin());
		TestCase.assertFalse(output.isActivatedFeature());
		TestCase.assertFalse(feature1.isActivatedFeature());
		TestCase.assertFalse(output.isSetup());
		
		pin.activateFeature(DigitalOutput.class);
		TestCase.assertTrue(output.isSetup());
		TestCase.assertFalse(feature1.isSetup());
		TestCase.assertEquals(pin, output.getPin());
		TestCase.assertTrue(output.isActivatedFeature());
		TestCase.assertFalse(feature1.isActivatedFeature());
		
		pin.activateFeature(MockedPinFeature1.class);
		TestCase.assertFalse(output.isSetup());
		TestCase.assertTrue(feature1.isSetup());
		TestCase.assertEquals(pin, output.getPin());
		TestCase.assertFalse(output.isActivatedFeature());
		TestCase.assertTrue(feature1.isActivatedFeature());
		
		output.activate();
		TestCase.assertTrue(output.isSetup());
		TestCase.assertFalse(feature1.isSetup());
		TestCase.assertEquals(pin, output.getPin());
		TestCase.assertTrue(output.isActivatedFeature());
		TestCase.assertFalse(feature1.isActivatedFeature());
		
		output.blockPin();
		TestCase.assertTrue(output.isBlocking());
		
		output.unblockPin();
		TestCase.assertFalse(output.isBlocking());
	}
	
}
