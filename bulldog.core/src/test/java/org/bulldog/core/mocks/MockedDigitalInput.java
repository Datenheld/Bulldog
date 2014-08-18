package org.bulldog.core.mocks;

import org.bulldog.core.Signal;
import org.bulldog.core.gpio.Pin;
import org.bulldog.core.gpio.base.AbstractDigitalInput;

public class MockedDigitalInput extends AbstractDigitalInput {

	private volatile Signal appliedSignal;
	
	public MockedDigitalInput(Pin pin) {
		super(pin);
		appliedSignal = Signal.Low;
	}
	
	public void setSignalToRead(Signal signal) {
		this.appliedSignal = signal;
	}
	
	public void bounceSignal(final Signal signal, final long bounceTimeMs) {
		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				long delta = 0;
				long start = System.currentTimeMillis();
				Signal nextSignal = signal;
				while(delta < bounceTimeMs) {
					setSignalToRead( nextSignal == Signal.High ? Signal.Low : Signal.High);
					delta = System.currentTimeMillis() - start;
				}
				
				setSignalToRead(signal);
			}
			
		});
		
		thread.setPriority(Thread.MAX_PRIORITY);
		thread.start();
	}

	@Override
	public Signal read() {
		return appliedSignal;
	}

	@Override
	protected void setupImpl() {
	}

	@Override
	protected void teardownImpl() {
	}


	@Override
	protected void enableInterruptsImpl() {
	}

	@Override
	protected void disableInterruptsImpl() {
	}


}
