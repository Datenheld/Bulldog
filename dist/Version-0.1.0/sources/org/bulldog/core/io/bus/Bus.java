package org.bulldog.core.io.bus;

import java.io.IOException;

import org.bulldog.core.io.IOPort;

public interface Bus extends IOPort {

	public void selectAddress(int address) throws IOException;
	public int getSelectedAddress();	
	public BusConnection createConnection(int address);
}
