package org.bulldog.core.io.bus.spi;

import org.bulldog.core.io.bus.BusConnection;

public class SpiConnection extends BusConnection {

	public SpiConnection(SpiBus bus, int address) {
		super(bus, address);
	}
	
	/**
	 * Full duplex transfer
	 * @param bytes
	 * @return
	 */
	public SpiMessage transfer(byte[] bytes) {
		return new SpiMessage();
	}

}
