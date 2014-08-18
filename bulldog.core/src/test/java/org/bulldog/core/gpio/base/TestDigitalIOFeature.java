package org.bulldog.core.gpio.base;

import junit.framework.TestCase;

import org.bulldog.core.Signal;
import org.bulldog.core.gpio.DigitalIO;
import org.bulldog.core.gpio.DigitalOutput;
import org.bulldog.core.gpio.Pin;
import org.bulldog.core.mocks.MockedDigitalInput;
import org.bulldog.core.mocks.MockedDigitalOutput;
import org.junit.Before;
import org.junit.Test;

public class TestDigitalIOFeature {

	private Pin pin;
	private GpioTester gpioTester;
	private MockedDigitalInput mockedInput;
	
	@Before
	public void setup() {
		pin = new Pin("Testpin", 0, "A", 0);
		mockedInput = new MockedDigitalInput(pin);
		MockedDigitalOutput output = new MockedDigitalOutput(pin);
		pin.addFeature(new DigitalIOFeature(pin, mockedInput, output));
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
	}
	
}
