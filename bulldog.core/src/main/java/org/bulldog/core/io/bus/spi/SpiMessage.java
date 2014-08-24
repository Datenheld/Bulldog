package org.bulldog.core.io.bus.spi;

public class SpiMessage {

	private byte[] receivedBytes;
	private byte[] sentBytes;
	
	public byte[] getReceivedBytes() {
		return receivedBytes;
	}
	
	public void setReceivedBytes(byte[] receivedBytes) {
		this.receivedBytes = receivedBytes;
	}
	
	public byte[] getSentBytes() {
		return sentBytes;
	}
	
	public void setSentBytes(byte[] sentBytes) {
		this.sentBytes = sentBytes;
	}
}
