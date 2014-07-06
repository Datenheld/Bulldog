package org.bulldog.core.gpio.base;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.bulldog.core.Edge;
import org.bulldog.core.Signal;
import org.bulldog.core.gpio.Pin;
import org.bulldog.core.gpio.event.InterruptEventArgs;
import org.bulldog.core.gpio.event.InterruptListener;
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
		TestCase.assertTrue(input.isSetup());
		input.setSignalToRead(Signal.High);
		TestCase.assertEquals(Signal.High, input.read());
		
		input.setSignalToRead(Signal.Low);
		TestCase.assertEquals(Signal.Low, input.read());
	}
	
	@Test
	public void testReadDebounced() {
		MockedDigitalInput input = pin.as(MockedDigitalInput.class);
		TestCase.assertTrue(input.isSetup());
		input.bounceSignal(Signal.High, 100);
		BulldogUtil.sleepMs(10);
		long start = System.currentTimeMillis();
		long delta = 0;
		while(delta < 100) {
			TestCase.assertEquals(Signal.High, input.readDebounced(100));
			delta = System.currentTimeMillis() - start;
		}
		
		input.bounceSignal(Signal.High, 20);
		BulldogUtil.sleepMs(5);
		while(delta < 100) {
			TestCase.assertEquals(Signal.High, input.readDebounced(30));
			delta = System.currentTimeMillis() - start;
		}
		
		input.bounceSignal(Signal.High, 5);
		BulldogUtil.sleepMs(5);
		while(delta < 100) {
			TestCase.assertEquals(Signal.High, input.readDebounced(100));
			delta = System.currentTimeMillis() - start;
		}
	}
	
	@Test
	public void testInterruptTrigger() {
		MockedDigitalInput input = pin.as(MockedDigitalInput.class);
		
		input.setInterruptTrigger(Edge.Rising);
		TestCase.assertEquals(Edge.Rising, input.getInterruptTrigger());
		
		input.setInterruptTrigger(Edge.Falling);
		TestCase.assertEquals(Edge.Falling, input.getInterruptTrigger());
		
		input.setInterruptTrigger(Edge.Both);
		TestCase.assertEquals(Edge.Both, input.getInterruptTrigger());
		
		input.setInterruptTrigger(Edge.Rising);
		TestCase.assertEquals(Edge.Rising, input.getInterruptTrigger());
	}
	
	@Test
	public void testInterrupts() {
		MockedDigitalInput input = pin.as(MockedDigitalInput.class);
		
		final List<Integer> counter = new ArrayList<Integer>();
		
		input.setInterruptDebounceMs(1000);
		TestCase.assertEquals(1000, input.getInterruptDebounceMs());
		
		TestCase.assertEquals(0, input.getInterruptListeners().size());
		input.addInterruptListener(new InterruptListener() {

			@Override
			public void interruptRequest(InterruptEventArgs args) {
				counter.add(1);
			}
			
		});
		
		TestCase.assertEquals(1, input.getInterruptListeners().size());
		
		input.addInterruptListener(new InterruptListener() {

			@Override
			public void interruptRequest(InterruptEventArgs args) {
				TestCase.assertEquals(Edge.Rising, args.getEdge());
				counter.add(1);
			}
			
		});
		

		TestCase.assertEquals(2, input.getInterruptListeners().size());
		TestCase.assertTrue(input.areInterruptsEnabled());
		input.fireInterruptEvent(new InterruptEventArgs(pin, Edge.Rising));
		TestCase.assertEquals(2, counter.size());
		
		input.disableInterrupts();
		TestCase.assertFalse(input.areInterruptsEnabled());
		input.fireInterruptEvent(new InterruptEventArgs(pin, Edge.Rising));
		TestCase.assertEquals(2,  counter.size());
		
		input.enableInterrupts();
		TestCase.assertTrue(input.areInterruptsEnabled());
		input.fireInterruptEvent(new InterruptEventArgs(pin, Edge.Rising));
		TestCase.assertEquals(4,  counter.size());
		
		input.removeInterruptListener(input.getInterruptListeners().get(0));
		TestCase.assertEquals(1, input.getInterruptListeners().size());
		
		input.clearInterruptListeners();
		TestCase.assertEquals(0, input.getInterruptListeners().size());
	}
	
	
	@Test
	public void testName() {
		MockedDigitalInput input = pin.as(MockedDigitalInput.class);
		TestCase.assertNotNull(input.getName());
	}
	
}
