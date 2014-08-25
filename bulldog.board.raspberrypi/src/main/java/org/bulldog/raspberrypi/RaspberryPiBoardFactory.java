package org.bulldog.raspberrypi;

import org.bulldog.core.platform.Board;
import org.bulldog.core.platform.BoardFactory;
import org.bulldog.linux.util.LinuxLibraryLoader;


public class RaspberryPiBoardFactory implements BoardFactory {

	@Override
	public boolean isCompatibleWithPlatform() {
		return true;
	}

	@Override
	public Board createBoard() {
		LinuxLibraryLoader.loadNativeLibrary();
		return new RaspberryPi();
	}

}