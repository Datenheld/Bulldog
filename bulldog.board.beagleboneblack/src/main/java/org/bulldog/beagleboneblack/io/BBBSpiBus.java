package org.bulldog.beagleboneblack.io;

import org.bulldog.core.platform.Board;
import org.bulldog.linux.io.LinuxSpiBus;

public class BBBSpiBus extends LinuxSpiBus {

	public BBBSpiBus(String name, String deviceFilePath, Board board) {
		super(name, deviceFilePath, board);
	}

}
