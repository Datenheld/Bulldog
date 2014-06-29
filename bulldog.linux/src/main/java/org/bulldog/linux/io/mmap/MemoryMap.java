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
	
	public void setValue(int offset, byte value) {
		NativeMmap.setValueAt(mmapPointer + offset, value);
	}
}
