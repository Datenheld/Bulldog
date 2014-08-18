package org.bulldog.core.platform;


public interface BoardFactory {

	boolean isCompatibleWithPlatform();
	Board createBoard();
	
}
