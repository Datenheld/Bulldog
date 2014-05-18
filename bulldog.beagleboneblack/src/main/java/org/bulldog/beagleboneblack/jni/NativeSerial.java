package org.bulldog.beagleboneblack.jni;

import java.nio.ByteBuffer;

public class NativeSerial {

	public static native int serialOpen(String devicePath, int baud);
	public static native int serialClose(int fileDescriptor);
	
	public static native byte serialRead(int fileDescriptor);
	public static native int serialReadBuffer(int fileDescriptor, ByteBuffer buffer, int bufferSize);
	public static native int serialDataAvailable(int fileDescriptor);
	
	public static native int serialWrite(int fileDescriptor, byte data);
	public static native int serialWriteBuffer(int fileDescriptor, ByteBuffer buffer, int bufferSize);	
}
