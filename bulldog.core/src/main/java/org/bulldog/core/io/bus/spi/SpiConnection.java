package org.bulldog.core.io.bus.spi;

import java.io.IOException;

import org.bulldog.core.io.bus.BusConnection;

public class SpiConnection extends BusConnection {

	public SpiConnection(SpiBus bus, int address) {
		super(bus, address);
	}
	
	/**
	 * Full duplex transfer
	 * @param bytes
	 * @return
	 * @throws IOException 
	 */
	public SpiMessage transfer(byte[] bytes) throws IOException {
		SpiBus bus = (SpiBus) this.getBus();
		bus.selectSlave(this.getAddress());
		SpiMessage message = bus.transfer(bytes);
		bus.deselectSlave(this.getAddress());
		return message;
	}
	
	public SpiBus getBus() {
		return (SpiBus)super.getBus();
	}

}
