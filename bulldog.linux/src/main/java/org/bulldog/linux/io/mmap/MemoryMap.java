package org.bulldog.linux.io.mmap;

import org.bulldog.linux.jni.NativeMmap;
import org.bulldog.linux.jni.NativeTools;

public class MemoryMap {

	private int fileDescriptor = 0;
	private long mmapPointer = 0;
	
	public MemoryMap(String filename, long offset, long size) {
		this(filename, offset, size, 0L);
	}
	
	public MemoryMap(String filename, long offset, long size, long address) {
		fileDescriptor = NativeTools.open(filename, NativeTools.OPEN_READ_WRITE);
		mmapPointer = NativeMmap.createMap(0, size, NativeMmap.READ | NativeMmap.WRITE, NativeMmap.SHARED, fileDescriptor, offset);
	}
	
	public void closeMap() {
		if(fileDescriptor == 0) { return; }
		NativeTools.close(fileDescriptor);
	}
	
	public void setLongValue(long offset, long value) {
		NativeMmap.setLongValueAt(mmapPointer + offset, value);
	}
	
	public long getLongValueAt(long offset) {
		return NativeMmap.getLongValueAt(mmapPointer + offset);
	}
	
	public void setIntValue(long offset, int value) {
		NativeMmap.setIntValueAt(mmapPointer + offset, value);
	}
	
	public int getIntValueAt(long offset) {
		return NativeMmap.getIntValueAt(mmapPointer + offset);
	}
	
	public void setShortValue(long offset, int value) {
		NativeMmap.setIntValueAt(mmapPointer + offset, value);
	}
	
	public short getShortValueAt(long offset) {
		return NativeMmap.getShortValueAt(mmapPointer + offset);
	}
	
	public void setByteValue(long offset, byte value) {
		NativeMmap.setIntValueAt(mmapPointer + offset, value);
	}
	
	public byte getByteValueAt(long offset) {
		return NativeMmap.getByteValueAt(mmapPointer + offset*2);
	}
}
