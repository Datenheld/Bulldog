package org.bulldog.raspberrypi;

import org.bulldog.core.platform.Board;
import org.bulldog.core.platform.BoardFactory;


public class RaspberryPiBoardFactory implements BoardFactory {

	@Override
	public boolean isCompatibleWithPlatform() {
		return true;
	}

	@Override
	public Board createBoard() {
		return RaspberryPi.getInstance();
	}

}