package org.bulldog.linux.jni;

public class NativeMmap {

	public static final int NONE  = 0x00;
	public static final int READ  = 0x01;
	public static final int WRITE = 0x02;
	public static final int EXEC  = 0x04;
	
	public static final int SHARED  = 0x01;
	public static final int PRIVATE = 0x02;
	
	public static native long createMap(long address, long length, int protection, int flags, int fileDescriptor, long offset);
	public static native int deleteMap(long address, long length);
	
	public static native void setLongValueAt(long address, long value);
	public static native long getLongValueAt(long address);
	
	public static native void setIntValueAt(long address, int value);
	public static native int getIntValueAt(long address);
	
	public static native void setShortValueAt(long address, short value);
	public static native short getShortValueAt(long address);
	
	public static native void setByteValueAt(long address, byte value);
	public static native byte getByteValueAt(long address);
}
