package org.bulldog.linux.jni;

import java.nio.ByteBuffer;

public class NativeSerial {

	public static final int SERIAL_BLOCK = 1;
	public static final int SERIAL_NO_BLOCK = 0;
	public static final int SERIAL_DEFAULT_TIMEOUT = 5;
	public static final int PARENB = 0000400;
	public static final int PARODD = 0001000;
	public static final int CMSPAR = 010000000000;

	public static native int serialOpen(String devicePath, int baud);
	public static native int serialOpen(String devicePath, int baud, int parity, boolean blocking, int readTimeout, int numDataBits, int numStopBits);
	public static native int serialClose(int fileDescriptor);
	public static native byte serialRead(int fileDescriptor);
	public static native int serialReadBuffer(int fileDescriptor, ByteBuffer buffer, int bufferSize);
	public static native int serialDataAvailable(int fileDescriptor);
	public static native int serialWrite(int fileDescriptor, byte data);
	public static native int serialWriteBuffer(int fileDescriptor, ByteBuffer buffer, int bufferSize);
}
