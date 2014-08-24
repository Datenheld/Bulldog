package org.bulldog.linux.jni;


public class NativeI2c {

	public static native byte i2cRead(int fileDescriptor);
	public static native int i2cWrite(int fileDescriptor, byte data);
	public static native int i2cSelectSlave(int busFileDescriptor, int slaveAddress);
	public static native int i2cOpen(String devicePath);
	public static native int i2cClose(int fileDescriptor);
	
}
