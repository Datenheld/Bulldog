package org.bulldog.linux.io;

import org.bulldog.core.util.BulldogUtil;
import org.bulldog.linux.jni.NativeEpoll;
import org.bulldog.linux.jni.NativePollResult;

public class LinuxSerialPortListener implements Runnable {

	private Thread listenerThread = new Thread(this);
	private boolean running = false;
	private int epollFd = 0;
	private boolean isSetup = false;
	private LinuxSerialPort serialPort;
	
	public LinuxSerialPortListener(LinuxSerialPort serialPort) {
		this.serialPort = serialPort;
		listenerThread.setDaemon(true);
		epollFd = NativeEpoll.epollCreate();
	}
	
	public void setup() {
		if(!isSetup) {
			NativeEpoll.addFile(epollFd, NativeEpoll.EPOLL_CTL_ADD, serialPort.getDeviceFilePath(), NativeEpoll.EPOLLPRI | NativeEpoll.EPOLLIN | NativeEpoll.EPOLLET);
			isSetup = true;
		}
	}
	
	public void start() {
		if(running) { return; }
		if(listenerThread.isAlive()) { return; }
		running = true;
		listenerThread.start();
	}
	
	public void stop() {
		if(!running) { return; }
		running = false;
		NativeEpoll.stopWait(epollFd);
		
		//block until thread is dead
		while(listenerThread.isAlive()) {
			BulldogUtil.sleepMs(10);
		}
	}

	public void run() {
		while(running) {
			
			NativePollResult[] results = NativeEpoll.waitForInterrupt(epollFd);
			if(results == null) { continue; }
			
			for(NativePollResult result : results) {
				serialPort.fireSerialDataEvent(result.getData());
			}
			
		}
	}
	
	public void shutdown() {
		stop();
		NativeEpoll.shutdown(epollFd);
	}
	
	public boolean isRunning() {
		return listenerThread.isAlive();
	}

}
