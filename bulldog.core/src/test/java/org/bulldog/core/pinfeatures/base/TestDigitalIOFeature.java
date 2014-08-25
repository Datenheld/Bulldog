package org.bulldog.core.pinfeatures.base;

import junit.framework.TestCase;

import org.bulldog.core.IODirection;
import org.bulldog.core.Signal;
import org.bulldog.core.mocks.MockedDigitalInput;
import org.bulldog.core.mocks.MockedDigitalOutput;
import org.bulldog.core.pinfeatures.DigitalIO;
import org.bulldog.core.pinfeatures.DigitalInput;
import org.bulldog.core.pinfeatures.DigitalOutput;
import org.bulldog.core.pinfeatures.Pin;
import org.bulldog.core.pinfeatures.PinFeatureConfiguration;
import org.junit.Before;
import org.junit.Test;

public class TestDigitalIOFeature {

	private Pin pin;
	private GpioTester gpioTester;
	private MockedDigitalInput mockedInput;
	private DigitalIOFeature feature;
	
	@Before
	public void setup() {
		pin = new Pin("Testpin", 0, "A", 0);
		mockedInput = new MockedDigitalInput(pin);
		MockedDigitalOutput output = new MockedDigitalOutput(pin);
		feature = new DigitalIOFeature(pin, mockedInput, output);
		pin.addFeature(feature);
		gpioTester = new GpioTester();
	}
	
	@Test
	public void testOutput() {
		gpioTester.testOutput(pin.as(DigitalIO.class));
	}
	
	@Test
	public void testName() {
		DigitalOutput output = pin.as(DigitalOutput.class);
		String name = output.getName();
		TestCase.assertNotNull(name);
	}
	
	@Test
	public void testBlinking() {
		gpioTester.testBlinking(pin.as(DigitalIO.class));
	}
	
	@Test
	public void testRead() {
		gpioTester.testRead(pin.as(DigitalIO.class), mockedInput);
	}
	
	@Test
	public void testReadDebounced() {
		gpioTester.testReadDebounced(pin.as(DigitalIO.class), mockedInput);
	}
	
	@Test
	public void testInterruptTrigger() {
		gpioTester.testInterruptTrigger(pin.as(DigitalIO.class));
	}
	
	@Test
	public void testInterrupts() {
		gpioTester.testInterrupts(pin.as(DigitalIO.class));
	}
	
	@Test
	public void testFeatureProvider() {
		DigitalIOFeature io = pin.as(DigitalIOFeature.class);
		TestCase.assertEquals(2, io.getFeatures().size());
		TestCase.assertTrue(io.hasFeature(DigitalInput.class));
		TestCase.assertTrue(io.hasFeature(DigitalOutput.class));
		
		DigitalOutput output = io.getFeature(DigitalOutput.class);
		TestCase.assertNotNull(output);
		
		MockedDigitalOutput mockedOutput = io.getFeature(MockedDigitalOutput.class);
		TestCase.assertNotNull(mockedOutput);
		
		DigitalInput input = io.getFeature(DigitalInput.class);
		TestCase.assertNotNull(input);
		
		MockedDigitalInput mockedInput = io.getFeature(MockedDigitalInput.class);
		TestCase.assertNotNull(mockedInput);
	}
	
	@Test
	public void testInputOutputSwitch() {
		DigitalIO io = pin.as(DigitalIO.class);
		TestCase.assertFalse(io.isOutputActive());
		TestCase.assertFalse(io.isInputActive());
		io.applySignal(Signal.High);
		TestCase.assertTrue(io.isOutputActive());
		TestCase.assertFalse(io.isInputActive());
		mockedInput.setSignalToRead(Signal.Low);
		TestCase.assertEquals(Signal.Low, io.read());
		TestCase.assertFalse(io.isOutputActive());
		TestCase.assertTrue(io.isInputActive());
		io.applySignal(Signal.High);
		TestCase.assertEquals(io.getAppliedSignal(), Signal.High);
		TestCase.assertTrue(io.isOutputActive());
		TestCase.assertFalse(io.isInputActive());
		
		io.setDirection(IODirection.Out);
		TestCase.assertFalse(io.isInputActive());
		TestCase.assertTrue(io.isOutputActive());
		TestCase.assertEquals(IODirection.Out, io.getDirection());
		
		io.setDirection(IODirection.In);
		TestCase.assertTrue(io.isInputActive());
		TestCase.assertFalse(io.isOutputActive());
		TestCase.assertEquals(IODirection.In, io.getDirection());
	}
	
	@Test
	public void testInputConfiguration() {
		DigitalInput input = pin.as(DigitalInput.class);
		TestCase.assertTrue(feature.isInputActive());
		TestCase.assertFalse(feature.isOutputActive());
		TestCase.assertTrue(input.isSetup());
		TestCase.assertFalse(pin.isFeatureActive(DigitalIO.class));
		TestCase.assertTrue(pin.isFeatureActive(DigitalInput.class));
		TestCase.assertFalse(pin.isFeatureActive(DigitalOutput.class));
		input.teardown();
		TestCase.assertFalse(input.isSetup());
	}
	
	@Test
	public void testOutputConfiguration() {
		DigitalOutput output = pin.as(DigitalOutput.class);
		TestCase.assertTrue(feature.isOutputActive());
		TestCase.assertFalse(feature.isInputActive());
		TestCase.assertTrue(output.isSetup());
		TestCase.assertFalse(pin.isFeatureActive(DigitalIO.class));
		TestCase.assertFalse(pin.isFeatureActive(DigitalInput.class));
		TestCase.assertTrue(pin.isFeatureActive(DigitalOutput.class));
		output.teardown();
		TestCase.assertFalse(output.isSetup());
	}
	
	@Test
	public void testInputOutputConfiguration() {
		DigitalIO io = pin.as(DigitalIO.class);
		TestCase.assertFalse(feature.isOutputActive());
		TestCase.assertFalse(feature.isInputActive());
		TestCase.assertTrue(io.isSetup());
		TestCase.assertTrue(pin.isFeatureActive(DigitalIO.class));
		TestCase.assertTrue(pin.isFeatureActive(DigitalInput.class));
		TestCase.assertTrue(pin.isFeatureActive(DigitalOutput.class));
		io.teardown();
		TestCase.assertFalse(io.isSetup());
	}
	
	@Test
	public void testSetupTeardown() {
		MockedDigitalOutput output = pin.as(new PinFeatureConfiguration(MockedDigitalOutput.class));
		TestCase.assertTrue(output.isSetup());
		MockedDigitalInput input = pin.as(new PinFeatureConfiguration(MockedDigitalInput.class));
		TestCase.assertTrue(input.isSetup());
		TestCase.assertFalse(output.isSetup());
		testInputOutputConfiguration();
		testOutputConfiguration();
		testInputConfiguration();
	}
	
}
