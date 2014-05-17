package org.bulldog.core.platform;

import java.util.Iterator;
import java.util.ServiceLoader;

public class Platform {

	public static Board detectBoard() throws IncompatiblePlatformException {
		Iterator<PlatformDetector> iter = ServiceLoader.load(PlatformDetector.class).iterator();
		while (iter.hasNext()) {
			PlatformDetector detector = iter.next();
			if(detector.isCompatibleWithPlatform()) {
				return detector.createBoard();
			}
		}
		
		throw new IncompatiblePlatformException();
	}
	
}
