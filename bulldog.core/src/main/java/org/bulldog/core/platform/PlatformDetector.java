package org.bulldog.core.platform;


public interface PlatformDetector {

	boolean isCompatibleWithPlatform();
	Board createBoard();
	
}
