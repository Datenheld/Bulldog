package org.bulldog.core.io.bus.spi;

import java.io.IOException;

import org.bulldog.core.io.bus.BusDevice;

public class SpiDevice extends BusDevice {

	public SpiDevice(SpiConnection connection) {
		super(connection);
	}
	
	public SpiDevice(SpiBus bus, int address) {
		this(bus.createSpiConnection(address));
	}
	
	public SpiMessage transfer(byte[] bytes) throws IOException {
		return getBusConnection().transfer(bytes);
	}
	
	public SpiConnection getBusConnection() {
		return (SpiConnection)super.getBusConnection();
	}

}
