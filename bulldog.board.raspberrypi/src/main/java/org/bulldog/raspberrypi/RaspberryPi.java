package org.bulldog.raspberrypi;

import org.bulldog.core.platform.AbstractBoard;

public class RaspberryPi extends AbstractBoard {

	private static final String NAME = "Raspberry Pi";
	
	public static RaspberryPi getInstance() {
		return null;
	}

	@Override
	public String getName() {
		return NAME;
	}
	
}
