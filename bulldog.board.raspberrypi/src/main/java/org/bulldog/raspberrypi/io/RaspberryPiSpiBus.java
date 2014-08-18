package org.bulldog.raspberrypi.io;

import org.bulldog.core.platform.Board;
import org.bulldog.linux.io.LinuxSpiBus;

public class RaspberryPiSpiBus extends LinuxSpiBus {

	public RaspberryPiSpiBus(String name, String deviceFilePath, Board board) {
		super(name, deviceFilePath, board);
	}

}
