package org.bulldog.core.gpio.util;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.bulldog.core.gpio.DigitalOutput;

public class Blinker implements Runnable {

	private ScheduledExecutorService executorService;
	private ScheduledFuture<?> future;
	private DigitalOutput output;
	private int durationMilliseconds = 0;
	private long startTime = 0;

	public Blinker(DigitalOutput output) {
		executorService = Executors.newScheduledThreadPool(1);
		this.output = output;
	}

	public void startBlinking(int periodLengthMilliseconds) {
		startBlinking(periodLengthMilliseconds, 0);
	}
	
	public void startBlinking(int periodLengthMilliseconds, int durationMilliseconds) {
		if(future != null) {
			future.cancel(true);
		}
		
		this.durationMilliseconds = durationMilliseconds;
		startTime = System.currentTimeMillis();
		output.setBlocking(true);
		future = executorService.scheduleAtFixedRate(this, 0,
				 periodLengthMilliseconds, TimeUnit.MILLISECONDS);
	}

	public void stopBlinking() {
		if(future == null) { return; }
		future.cancel(true);
		future = null;
		output.setBlocking(false);
	}

	@Override
	public void run() {
		if(durationMilliseconds > 0) {
			long delta = System.currentTimeMillis() - startTime;
			if(delta >= durationMilliseconds) { stopBlinking(); }
		}
		
		output.toggle();
	}
}
