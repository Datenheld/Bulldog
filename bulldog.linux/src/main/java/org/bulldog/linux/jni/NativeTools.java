package org.bulldog.linux.jni;

import java.io.FileDescriptor;

public class NativeTools {

	public static final int OPEN_READ_ONLY  = 0x00;
	public static final int OPEN_WRITE_ONLY = 0x01;
	public static final int OPEN_READ_WRITE = 0x02;
	
	public static native FileDescriptor getJavaDescriptor(int fileDescriptor);
	public static native int open(String filename, int flags);
	public static native int close(int fileDescriptor);
	
}
