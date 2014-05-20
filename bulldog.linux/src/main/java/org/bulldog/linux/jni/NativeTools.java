package org.bulldog.linux.jni;

import java.io.FileDescriptor;

public class NativeTools {

	public static native FileDescriptor getJavaDescriptor(int fileDescriptor);
	
}
