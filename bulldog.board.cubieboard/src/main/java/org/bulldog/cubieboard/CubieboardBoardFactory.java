package org.bulldog.cubieboard;

import org.bulldog.core.platform.Board;
import org.bulldog.core.platform.BoardFactory;


public class CubieboardBoardFactory implements BoardFactory {

	@Override
	public boolean isCompatibleWithPlatform() {
		return true;
	}

	@Override
	public Board createBoard() {
		return Cubieboard.getInstance();
	}
}