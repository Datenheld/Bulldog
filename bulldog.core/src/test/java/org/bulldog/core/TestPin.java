package org.bulldog.core;

import junit.framework.Assert;

import org.bulldog.core.gpio.Pin;
import org.bulldog.core.mocks.MockedPinFeatureType1;
import org.bulldog.core.mocks.MockedPinFeatureType2;
import org.bulldog.core.mocks.MockedPinFeatureType3;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class TestPin {

	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	@Test
	public void testFeatureActivation() {

	}
	
	@Test
	public void testAddFeature() {
		Pin pin = new Pin("Testpin", 0, "A", 0);
		MockedPinFeatureType1 type1 = new MockedPinFeatureType1(pin);
		MockedPinFeatureType2 type2 = new MockedPinFeatureType2(pin);
		MockedPinFeatureType3 type3 = new MockedPinFeatureType3(pin);
		pin.addFeature(type1)
		   .addFeature(type2)
		   .addFeature(type3);
		   
		exception.expect(IllegalArgumentException.class);
		pin.addFeature(type3);
		
		Assert.assertEquals(0, pin.getFeatures().size());
	}
	
	@Test
	public void testRemoveFeature() {
		
	}
	
	@Test
	public void testAs() {
		
	}
	
	@Test
	public void testBlocking() {
		
	}
	
	@Test
	public void testFeatureAvailabilityCheck() {
		
	}
	
	@Test
	public void testGetFeature() {
		
	}
	
	@Test
	public void testHasFeature() {
		
	}
	
	@Test
	public void testGetName() {
		
	}
		
}
