package org.bulldog.core.platform;

import java.util.List;

import org.bulldog.core.gpio.Pin;
import org.bulldog.core.io.IOPort;
import org.bulldog.core.io.SerialPort;
import org.bulldog.core.io.bus.i2c.I2cBus;

public interface Board {

	public String getName();
	public List<Pin> getPins();
	public Pin getPinByAlias(String alias);
	public Pin getPin(int address);
	public Pin getPin(String name);
	public Pin getPin(String port, int indexOnPort);
	
	public List<I2cBus> getI2cBuses();
	public I2cBus getI2cBus(String name);
	public List<SerialPort> getSerialPorts();
	public SerialPort getSerialPort(String name);
	List<IOPort> getAllIOPorts();
	public IOPort getIOPortByAlias(String alias);
	public IOPort getIOPortByName(String name);
}
