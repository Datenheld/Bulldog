package org.bulldog.beagleboneblack.gpio;

import org.bulldog.beagleboneblack.jni.NativeEpoll;
import org.bulldog.beagleboneblack.jni.NativePollResult;
import org.bulldog.core.Edge;
import org.bulldog.core.gpio.DigitalInput;
import org.bulldog.core.gpio.event.InterruptEventArgs;
import org.bulldog.core.util.BulldogUtil;

public class BBBPinInterruptControl implements Runnable {

	private DigitalInput interrupt;
	private Thread interruptThread = new Thread(this);
	private boolean running = false;
	private Edge lastEdge = null;
	private int epollFd = 0;
	private int debounceMilliseconds = 0;
	private volatile long lastInterruptTime;
	private boolean isSetup = false;
	private String filename;
	
	public BBBPinInterruptControl(DigitalInput interrupt, String filename) {
		this.interrupt = interrupt;
		this.filename = filename;
		interruptThread.setDaemon(true);
		epollFd = NativeEpoll.epollCreate();
	}
	
	public void setup() {
		if(!isSetup) {
			NativeEpoll.addFile(epollFd, NativeEpoll.EPOLL_CTL_ADD, filename, NativeEpoll.EPOLLPRI | NativeEpoll.EPOLLET);
			isSetup = true;
		}
	}
	
	public void setDebounceMilliseconds(int milliSeconds) {
		this.debounceMilliseconds = milliSeconds;
	}
	
	public void start() {
		System.out.println("START LISTEN ON " + filename);
		if(running) { return; }
		if(interruptThread.isAlive()) { return; }
		running = true;
		interruptThread.start();
	}
	
	public void stop() {
		if(!running) { return; }
		lastEdge = null;
		running = false;
		NativeEpoll.stopWait(epollFd);
		
		//block until thread is dead
		while(interruptThread.isAlive()) {
			BulldogUtil.sleepMs(10);
		}
	}

	public void run() {
		while(running) {
			
			NativePollResult[] results = NativeEpoll.waitForInterrupt(epollFd);
			if(results == null) { continue; }
			
			for(NativePollResult result : results) {
				if(lastEdge != null && lastEdge.equals(result.getEdge())) { continue; }
				
				Edge edge = result.getEdge();
				long delta = System.currentTimeMillis() - lastInterruptTime;
				if(delta <= this.debounceMilliseconds) {
					continue;
				}
				
				lastInterruptTime = System.currentTimeMillis();
				lastEdge = result.getEdge();
				interrupt.fireInterruptEvent(new InterruptEventArgs(edge));
			}
			
		}
	}
	
	public void shutdown() {
		stop();
		NativeEpoll.shutdown(epollFd);
	}
	
	public boolean isRunning() {
		return interruptThread.isAlive();
	}

}
