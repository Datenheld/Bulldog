package org.bulldog.beagleboneblack;

import org.bulldog.core.platform.Board;
import org.bulldog.core.platform.BoardFactory;

public class BeagleBoneBlackBoardFactory implements BoardFactory {

	@Override
	public boolean isCompatibleWithPlatform() {
		return true;
	}

	@Override
	public Board createBoard() {
		return BeagleBoneBlack.getInstance();
	}

}
