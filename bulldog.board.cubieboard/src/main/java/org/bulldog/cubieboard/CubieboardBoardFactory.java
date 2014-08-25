package org.bulldog.cubieboard;

import org.bulldog.core.platform.Board;
import org.bulldog.core.platform.BoardFactory;
import org.bulldog.linux.util.LinuxLibraryLoader;


public class CubieboardBoardFactory implements BoardFactory {

	@Override
	public boolean isCompatibleWithPlatform() {
		return true;
	}

	@Override
	public Board createBoard() {
		LinuxLibraryLoader.loadNativeLibrary();
		return new Cubieboard();
	}
}