package org.bulldog.core.gpio.base;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.bulldog.core.Edge;
import org.bulldog.core.Signal;
import org.bulldog.core.gpio.DigitalInput;
import org.bulldog.core.gpio.DigitalOutput;
import org.bulldog.core.gpio.event.InterruptEventArgs;
import org.bulldog.core.gpio.event.InterruptListener;
import org.bulldog.core.mocks.MockedDigitalInput;
import org.bulldog.core.util.BulldogUtil;

public final class GpioTester {

	public void testOutput(DigitalOutput output) {
		TestCase.assertTrue(output.isSetup());
		
		TestCase.assertEquals(Signal.Low, output.getAppliedSignal());
		
		output.toggle();
		
		TestCase.assertEquals(Signal.High, output.getAppliedSignal());
		
		output.toggle();
		
		TestCase.assertEquals(Signal.Low, output.getAppliedSignal());
		
		output.applySignal(Signal.Low);
		
		TestCase.assertEquals(Signal.Low, output.getAppliedSignal());
		
		output.applySignal(Signal.High);
		
		TestCase.assertEquals(Signal.High, output.getAppliedSignal());
		
		output.applySignal(Signal.High);
		TestCase.assertEquals(Signal.High, output.getAppliedSignal());
		
		output.applySignal(Signal.Low);
		
		TestCase.assertEquals(Signal.Low, output.getAppliedSignal());
		
		output.applySignal(Signal.High);
		output.toggle();
		
		TestCase.assertEquals(Signal.Low, output.getAppliedSignal());
		
		output.high();
		
		TestCase.assertEquals(Signal.High, output.getAppliedSignal());
		
		output.low();
		
		TestCase.assertEquals(Signal.Low, output.getAppliedSignal());
		
		output.high();
		TestCase.assertTrue(output.isHigh());
		TestCase.assertFalse(output.isLow());
		
		output.toggle();
		TestCase.assertFalse(output.isHigh());
		TestCase.assertTrue(output.isLow());
	
		output.write(Signal.High);
		TestCase.assertTrue(output.isHigh());
		TestCase.assertFalse(output.isLow());
		
		output.write(Signal.Low);
		TestCase.assertFalse(output.isHigh());
		TestCase.assertTrue(output.isLow());
	}
	
	public void testBlinking(DigitalOutput output) {
		TestCase.assertFalse(output.isBlocking());
		output.stopBlinking(); // should do nothing
		TestCase.assertFalse(output.isBlocking());
		TestCase.assertFalse(output.isBlinking());
		
		output.startBlinking(50);
		testBlinking(output, 1000, 50);
		output.stopBlinking();
		output.awaitBlinkingStopped();
		TestCase.assertFalse(output.isBlinking());
		
		output.startBlinking(200);
		testBlinking(output, 1000, 200);
		
		output.stopBlinking();
		TestCase.assertFalse(output.isBlocking());
		
		output.startBlinking(50, 1000);
		TestCase.assertTrue(output.isBlocking());
		BulldogUtil.sleepMs(1050);
		TestCase.assertFalse(output.isBlocking());
		
		try {
			output.blinkTimes(10, 0);
			TestCase.fail();
		} catch(IllegalArgumentException ex) {}
		
		try {
			output.blinkTimes(10, -1);
			TestCase.fail();
		} catch(IllegalArgumentException ex) {}
		
		Signal signal = output.getAppliedSignal();
		output.blinkTimes(10, 3);
		for(int i = 0; i < 3; i++) {
			BulldogUtil.sleepMs(5);
			TestCase.assertEquals(signal.inverse(), output.getAppliedSignal());
			TestCase.assertTrue(output.isBlinking());
			BulldogUtil.sleepMs(5);
			TestCase.assertEquals(signal,  output.getAppliedSignal());
		}
		TestCase.assertFalse(output.isBlinking());
				
		BulldogUtil.sleepMs(10);
		TestCase.assertEquals(signal,  output.getAppliedSignal());
		BulldogUtil.sleepMs(5);
		TestCase.assertEquals(signal,  output.getAppliedSignal());
		BulldogUtil.sleepMs(5);
		TestCase.assertEquals(signal,  output.getAppliedSignal());
		
		output.startBlinking(50, 500);
		TestCase.assertTrue(output.isBlocking());
		testBlinking(output, 200, 50);
		output.awaitBlinkingStopped();
		TestCase.assertFalse(output.isBlocking());
		TestCase.assertFalse(output.isBlinking());
	}

	private void testBlinking(DigitalOutput output, long duration, long blinkPeriod) {
		long startTime = System.currentTimeMillis();
		long delta = 0;
		Signal expectedSignal = output.getAppliedSignal();
		while(delta < duration) {
			expectedSignal = expectedSignal.inverse();
			BulldogUtil.sleepMs((int)blinkPeriod);
			delta = System.currentTimeMillis() - startTime;	
			Signal signal = output.getAppliedSignal();
			expectedSignal = signal;
			TestCase.assertEquals(expectedSignal, signal);
			TestCase.assertTrue(output.isBlocking());
			TestCase.assertTrue(output.isBlinking());
		}
	}
	
	public void testRead(DigitalInput input, MockedDigitalInput mock) {
		TestCase.assertTrue(input.isSetup());
		mock.setSignalToRead(Signal.High);
		TestCase.assertEquals(Signal.High, input.read());
		
		mock.setSignalToRead(Signal.Low);
		TestCase.assertEquals(Signal.Low, input.read());
	}
	
	public void testReadDebounced(DigitalInput input, MockedDigitalInput mock) {
		TestCase.assertTrue(input.isSetup());
		mock.bounceSignal(Signal.High, 100);
		BulldogUtil.sleepMs(10);
		long start = System.currentTimeMillis();
		long delta = 0;
		while(delta < 100) {
			TestCase.assertEquals(Signal.High, input.readDebounced(100));
			delta = System.currentTimeMillis() - start;
		}
		
		mock.bounceSignal(Signal.High, 20);
		BulldogUtil.sleepMs(5);
		while(delta < 100) {
			TestCase.assertEquals(Signal.High, input.readDebounced(30));
			delta = System.currentTimeMillis() - start;
		}
		
		mock.bounceSignal(Signal.High, 5);
		BulldogUtil.sleepMs(5);
		while(delta < 100) {
			TestCase.assertEquals(Signal.High, input.readDebounced(100));
			delta = System.currentTimeMillis() - start;
		}
	}
	
	public void testInterruptTrigger(DigitalInput input) {
		input.setInterruptTrigger(Edge.Rising);
		TestCase.assertEquals(Edge.Rising, input.getInterruptTrigger());
		
		input.setInterruptTrigger(Edge.Falling);
		TestCase.assertEquals(Edge.Falling, input.getInterruptTrigger());
		
		input.setInterruptTrigger(Edge.Both);
		TestCase.assertEquals(Edge.Both, input.getInterruptTrigger());
		
		input.setInterruptTrigger(Edge.Rising);
		TestCase.assertEquals(Edge.Rising, input.getInterruptTrigger());
	}
	
	public void testInterrupts(DigitalInput input) {
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
		input.fireInterruptEvent(new InterruptEventArgs(input.getPin(), Edge.Rising));
		TestCase.assertEquals(2, counter.size());
		
		input.disableInterrupts();
		TestCase.assertFalse(input.areInterruptsEnabled());
		input.fireInterruptEvent(new InterruptEventArgs(input.getPin(), Edge.Rising));
		TestCase.assertEquals(2,  counter.size());
		
		input.enableInterrupts();
		TestCase.assertTrue(input.areInterruptsEnabled());
		input.fireInterruptEvent(new InterruptEventArgs(input.getPin(), Edge.Rising));
		TestCase.assertEquals(4,  counter.size());
		
		input.removeInterruptListener(input.getInterruptListeners().get(0));
		TestCase.assertEquals(1, input.getInterruptListeners().size());
		
		input.clearInterruptListeners();
		TestCase.assertEquals(0, input.getInterruptListeners().size());
	}
	
	
}
