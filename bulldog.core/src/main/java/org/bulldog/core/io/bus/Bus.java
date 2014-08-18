package org.bulldog.core.io.bus;

import java.io.IOException;

import org.bulldog.core.io.IOPort;

public interface Bus extends IOPort {

	public void selectSlave(int address) throws IOException;
	public boolean isSlaveSelected(int address);
	public BusConnection createConnection(int address);
}
