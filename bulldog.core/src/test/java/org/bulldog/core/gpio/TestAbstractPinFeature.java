package org.bulldog.core.gpio;

import junit.framework.TestCase;

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
		
		pin.activateFeature(DigitalOutput.class);
		TestCase.assertEquals(pin, output.getPin());
		TestCase.assertTrue(output.isActivatedFeature());
		TestCase.assertFalse(feature1.isActivatedFeature());
		
		pin.activateFeature(MockedPinFeature1.class);
		TestCase.assertEquals(pin, output.getPin());
		TestCase.assertFalse(output.isActivatedFeature());
		TestCase.assertTrue(feature1.isActivatedFeature());
		
		output.activate();
		TestCase.assertEquals(pin, output.getPin());
		TestCase.assertTrue(output.isActivatedFeature());
		TestCase.assertFalse(feature1.isActivatedFeature());
		
		output.setBlocking(true);
		TestCase.assertTrue(output.isBlocking());
		
		output.setBlocking(false);
		TestCase.assertFalse(output.isBlocking());
	}
	
}
