package org.bulldog.core.platform;

import java.util.Iterator;
import java.util.ServiceLoader;

public class Platform {

	public static Board createBoard() throws IncompatiblePlatformException {
		Iterator<BoardFactory> iter = ServiceLoader.load(BoardFactory.class).iterator();
		while (iter.hasNext()) {
			BoardFactory detector = iter.next();
			if(detector.isCompatibleWithPlatform()) {
				return detector.createBoard();
			}
		}
		
		throw new IncompatiblePlatformException();
	}
}
