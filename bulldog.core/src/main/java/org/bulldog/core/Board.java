package org.bulldog.core;

import java.util.List;

import org.bulldog.core.bus.Bus;
import org.bulldog.core.bus.I2cBus;
import org.bulldog.core.bus.SerialBus;
import org.bulldog.core.gpio.Pin;

public interface Board {

	public String getName();
	public List<Pin> getPins();
	public Pin getPinByAddress(int address);
	public Pin getPinByName(String name);
	public Pin getPinByAlias(String alias);
	
	public List<I2cBus> getI2cBuses();
	public List<SerialBus> getSerialBuses();
	public Bus getBusByAlias(String alias);
	public Bus getBusByName(String name);
}
