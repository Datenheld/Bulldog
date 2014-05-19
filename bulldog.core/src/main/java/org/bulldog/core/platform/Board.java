package org.bulldog.core.platform;

import java.util.List;

import org.bulldog.core.gpio.Pin;
import org.bulldog.core.io.IOPort;
import org.bulldog.core.io.SerialPort;
import org.bulldog.core.io.bus.I2cBus;

public interface Board {

	public String getName();
	public List<Pin> getPins();
	public Pin getPinByAddress(int address);
	public Pin getPinByName(String name);
	public Pin getPinByAlias(String alias);
	public Pin getPin(String port, int indexOnPort);
	
	public List<I2cBus> getI2cBuses();
	public List<SerialPort> getSerialPorts();
	List<IOPort> getAllIOPorts();
	public IOPort getIOPortByAlias(String alias);
	public IOPort getIOPortByName(String name);
}
