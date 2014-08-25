package org.bulldog.core.gpio.util;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.bulldog.core.gpio.DigitalOutput;
import org.bulldog.core.util.BulldogUtil;
import org.bulldog.core.util.DaemonThreadFactory;

public class Blinker implements Runnable {

	private ScheduledExecutorService executorService;
	private ScheduledFuture<?> future;
	private DigitalOutput output;
	private int durationMilliseconds = 0;
	private long startTime = 0;
	private int times = 0;
	private boolean doTimes = false;

	public Blinker(DigitalOutput output) {
		executorService = Executors.newScheduledThreadPool(1, new DaemonThreadFactory());
		this.output = output;
	}

	public void startBlinking(int periodLengthMilliseconds) {
		startBlinking(periodLengthMilliseconds, 0);
	}
	
	public void blinkTimes(int periodLengthMilliseconds, int times) {
		if(times <= 0) {
			throw new IllegalArgumentException("You must specify to blink at least one time");
		}
		this.times = times * 2;
		doTimes = true;
		startBlinking(periodLengthMilliseconds);
	}
	
	public void startBlinking(int periodLengthMilliseconds, int durationMilliseconds) {
		if(future != null) {
			future.cancel(true);
		}
		
		this.durationMilliseconds = durationMilliseconds;
		startTime = System.currentTimeMillis();
		output.blockPin();
		future = executorService.scheduleAtFixedRate(this, 0,
				 periodLengthMilliseconds / 2, TimeUnit.MILLISECONDS);
	}

	public void stopBlinking() {
		if(future == null) { return; }
		future.cancel(true);
		future = null;
		doTimes = false;
		output.unblockPin();
	}

	@Override
	public void run() {
		if(!doTimes) {
			if(durationMilliseconds > 0) {
				long delta = System.currentTimeMillis() - startTime;
				if(delta >= durationMilliseconds) { stopBlinking(); }
			}
		} else {
			times = times - 1;
			if(times == 0) { stopBlinking(); doTimes = false; }
		}
				
		output.toggle();
	}
	
	public boolean isBlinking() {
		if(this.future != null) {
			return !future.isDone();
		}
		
		return false;
	}
	
	public void awaitBlinkingStopped() {
		while(isBlinking()) {
			BulldogUtil.sleepMs(1);
		}
	}
}
