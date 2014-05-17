package org.bulldog.beagleboneblack;

import org.bulldog.core.platform.Board;
import org.bulldog.core.platform.PlatformDetector;

public class BeagleBoneBlackPlatformDetector implements PlatformDetector {

	@Override
	public boolean isCompatibleWithPlatform() {
		return true;
	}

	public Board createBoard() {
		return new BeagleBoneBlack();
	}
}
