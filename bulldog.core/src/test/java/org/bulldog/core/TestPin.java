package org.bulldog.core;

import junit.framework.TestCase;

import org.bulldog.core.gpio.Pin;
import org.bulldog.core.gpio.PinFeature;
import org.bulldog.core.mocks.MockedPinFeatureType1;
import org.bulldog.core.mocks.MockedPinFeatureType2;
import org.bulldog.core.mocks.MockedPinFeatureType3;
import org.junit.Test;

public class TestPin {
	
	@Test
	public void testFeatureActivation() {
		Pin pin = new Pin("Testpin", 0, "A", 0);
		MockedPinFeatureType1 type1 = new MockedPinFeatureType1(pin);
		MockedPinFeatureType2 type2 = new MockedPinFeatureType2(pin);
		MockedPinFeatureType3 type3 = new MockedPinFeatureType3(pin);
		
		pin.addFeature(type3)
		   .addFeature(type2)
		   .addFeature(type1);
		
		pin.activateFeature(MockedPinFeatureType2.class);
		PinFeature feature = pin.getActiveFeature();
		TestCase.assertEquals(type2, feature);

		pin.activateFeature(MockedPinFeatureType3.class);
		feature = pin.getActiveFeature();
		TestCase.assertEquals(type3, feature);
	
		feature = pin.as(MockedPinFeatureType2.class);
		TestCase.assertEquals(type2, feature);
	
		feature = pin.as(MockedPinFeatureType3.class);
		TestCase.assertEquals(type3, feature);
		feature = pin.getActiveFeature();
		TestCase.assertEquals(type3, feature);
		
		pin.removeFeature(MockedPinFeatureType2.class);
		TestCase.assertEquals(2, pin.getFeatures().size());
		feature = pin.as(MockedPinFeatureType2.class);  //type3 is still present and extends type2
		TestCase.assertEquals(type3, feature);
		
		pin.removeFeature(MockedPinFeatureType2.class);
	}
	
	@Test
	public void testAddRemoveFeature() {
		Pin pin = new Pin("Testpin", 0, "A", 0);
		MockedPinFeatureType1 type1 = new MockedPinFeatureType1(pin);
		MockedPinFeatureType2 type2 = new MockedPinFeatureType2(pin);
		MockedPinFeatureType3 type3 = new MockedPinFeatureType3(pin);
		pin.addFeature(type1)
		   .addFeature(type2)
		   .addFeature(type3);
		
		try {
			pin.addFeature(type3);
			TestCase.fail();
		} catch(IllegalArgumentException ex) {}
		
		TestCase.assertTrue(pin.hasFeature(MockedPinFeatureType2.class));
		TestCase.assertEquals(3, pin.getFeatures().size());
		pin.removeFeature(MockedPinFeatureType2.class);
		TestCase.assertEquals(2, pin.getFeatures().size());
		TestCase.assertTrue(pin.hasFeature(MockedPinFeatureType2.class)); //type 3 inherits and is still present
		pin.removeFeature(MockedPinFeatureType2.class);
		TestCase.assertEquals(2, pin.getFeatures().size());
		pin.removeFeature(MockedPinFeatureType3.class);
		TestCase.assertEquals(1, pin.getFeatures().size());
		TestCase.assertFalse(pin.hasFeature(MockedPinFeatureType3.class)); 
		
		PinFeature feature = pin.as(MockedPinFeatureType1.class);
		TestCase.assertEquals(pin.getActiveFeature(), feature);
		pin.removeFeature(MockedPinFeatureType1.class);
		TestCase.assertNull(pin.getActiveFeature());
		try {
			feature = pin.as(MockedPinFeatureType1.class);
			TestCase.fail();
		} catch(IllegalArgumentException ex) {}
	}
		
	@Test
	public void testBlocking() {
		
	}
}
