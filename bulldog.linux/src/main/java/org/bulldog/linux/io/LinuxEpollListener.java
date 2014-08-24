package org.bulldog.linux.io;

import org.bulldog.linux.jni.NativePollResult;

public interface LinuxEpollListener {

	void processEpollResults(NativePollResult[] results);
	
}
