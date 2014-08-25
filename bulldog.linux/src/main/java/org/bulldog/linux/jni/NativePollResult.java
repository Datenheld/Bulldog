package org.bulldog.linux.jni;

import org.bulldog.core.util.BulldogUtil;

public class NativePollResult {

	private int events;
	private int fd;
	private byte[] data;
	
	public NativePollResult(int fd, int events, byte[] data) {
		this.fd = fd;
		this.events = events;
		this.data = data;
	}

	public int getEvents() {
		return events;
	}

	public int getFileDescriptor() {
		return fd;
	}
	
	public byte[] getData() {
		return data;
	}

	public String getDataAsString() {
		return BulldogUtil.bytesToString(getData());
	}
}

