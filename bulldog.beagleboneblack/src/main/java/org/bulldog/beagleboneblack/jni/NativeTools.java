package org.bulldog.beagleboneblack.jni;

import java.io.FileDescriptor;

public class NativeTools {

	public static native FileDescriptor getJavaDescriptor(int fileDescriptor);
	
}
