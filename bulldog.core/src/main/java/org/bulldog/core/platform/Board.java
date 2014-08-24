package org.bulldog.core.platform;

import java.util.List;
import java.util.Properties;

import org.bulldog.core.io.IOPort;
import org.bulldog.core.io.bus.i2c.I2cBus;
import org.bulldog.core.io.bus.spi.SpiBus;
import org.bulldog.core.io.serial.SerialPort;

public interface Board extends PinProvider {

	String getName();
	
	List<I2cBus> getI2cBuses();
	I2cBus getI2cBus(String name);
	
	List<SpiBus> getSpiBuses();
	SpiBus getSpiBus(String name);
	
	List<SerialPort> getSerialPorts();
	SerialPort getSerialPort(String name);
	
	List<IOPort> getAllIOPorts();
	IOPort getIOPortByAlias(String alias);
	IOPort getIOPortByName(String name);
	
	Properties getProperties();
	void setProperty(String propertyName, String value);
	String getProperty(String propertyName);
	boolean hasProperty(String propertyName);
	
	void shutdown();
}
