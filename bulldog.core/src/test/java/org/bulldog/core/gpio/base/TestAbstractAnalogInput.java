package org.bulldog.core.gpio.base;

import junit.framework.TestCase;

import org.bulldog.core.gpio.Pin;
import org.bulldog.core.gpio.event.ThresholdListener;
import org.bulldog.core.mocks.MockedAnalogInput;
import org.bulldog.core.util.BulldogUtil;
import org.junit.Before;
import org.junit.Test;

public class TestAbstractAnalogInput {

	private Pin pin;
	
	@Before
	public void setup() {
		pin = new Pin("Testpin", 0, "A", 0);
		MockedAnalogInput input = new MockedAnalogInput(pin);
		pin.addFeature(input);
	}
	
	@Test
	public void testName() {
		MockedAnalogInput input = pin.as(MockedAnalogInput.class);
		String name = input.getName();
		TestCase.assertNotNull(name);
	}
	
	@Test
	public void testMonitoring() {
		MockedAnalogInput input = pin.as(MockedAnalogInput.class);
		TestCase.assertFalse(input.isBlocking());
		TestCase.assertTrue(input.isSetup());
		
		double[] samples = new double[20];
		for(int i = 0; i < 20; i++) {
			samples[i] = Math.sin(Math.PI / i);
		}
		
		input.setSamples(samples);
		input.stopMonitor(); // should do nothing
		TestCase.assertFalse(input.isBlocking());
		
		input.startMonitor(10, new ThresholdListener() {

			@Override
			public void thresholdReached() {
				
			}

			@Override
			public boolean isThresholdReached(double thresholdValue) {
				return thresholdValue >= 0.5;
			}
			
		});
		TestCase.assertTrue(input.isBlocking());
		BulldogUtil.sleepMs(1000);
		input.stopMonitor();
		TestCase.assertFalse(input.isBlocking());
	}
	
}
