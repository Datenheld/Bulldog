package org.bulldog.linux.jni;

import java.nio.ByteBuffer;

public class NativeSpi {

	public static native int spiOpen(String filename, int speed, int bitsPerWord, int mode);
	public static native int spiClose(int fileDescriptor);
	public static native int spiTransfer(int fileDescriptor, ByteBuffer txBuffer, ByteBuffer rxBuffer, int transferLength, int delay, int speed, int bitsPerWord);
		
}
