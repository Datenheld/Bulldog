package org.bulldog.core.platform;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.bulldog.core.gpio.Pin;
import org.bulldog.core.io.IOPort;
import org.bulldog.core.io.bus.i2c.I2cBus;
import org.bulldog.core.io.serial.SerialPort;

public abstract class AbstractBoard implements Board {

	private List<Pin> pins = new ArrayList<Pin>();
	private List<I2cBus> i2cBuses = new ArrayList<I2cBus>();
	private List<SerialPort> serialBuses = new ArrayList<SerialPort>();
	private Properties properties = new Properties();

	public List<Pin> getPins() {
		return  pins;
	}

	public Pin getPin(int address) {
		for(Pin pin : getPins()) {
			if(pin.getAddress() == address) {
				return pin;
			}
		}
		
		return null;
	}
	
	public Pin getPin(String port, int index) {
		if(port == null) 	{ throw new IllegalArgumentException("Null may not be passed as a name for a port."); }
		if(index < 0) 		{ throw new IllegalArgumentException("Index cannot be smaller than 0"); }
		
		for(Pin pin : getPins()) {
			if(port.equals(pin.getPort()) && index == pin.getIndexOnPort()) {
				return pin;
			}
		}
		
		return null;
	}

	public Pin getPin(String name) {
		if(name == null) 	{ throw new IllegalArgumentException("Null may not be passed as a name for a pin."); }
		
		for(Pin pin : getPins()) {
			if(name.equals(pin.getName())) {
				return pin;
			}
		}
		
		return null;
	}
	
	public Pin getPinByAlias(String alias) {
		if(alias == null) 	{ throw new IllegalArgumentException("Null may not be passed as an alias name for a pin."); }
		
		for(Pin pin : getPins()) {
			if(alias.equals(pin.getAlias())) {
				return pin;
			}
		}
		
		return null;
	}
		
	public IOPort getIOPortByAlias(String alias) {
		if(alias == null) 	{ throw new IllegalArgumentException("Null may not be passed as an alias name for an IOPort."); }
	
		for(IOPort port : getAllIOPorts()) {
			if(alias.equals(port.getAlias())) {
				return port;
			}
		}
		
		return null;
	}
	
	public IOPort getIOPortByName(String name) {
		if(name == null) 	{ throw new IllegalArgumentException("Null may not be passed as a name for an IOPort."); }
	
		for(IOPort port : getAllIOPorts()) {
			if(name.equals(port.getName())) {
				return port;
			}
		}
		
		return null;
	}

	public List<IOPort> getAllIOPorts() {
		List<IOPort> ioPorts = new ArrayList<IOPort>();
		ioPorts.addAll(getI2cBuses());
		ioPorts.addAll(getSerialPorts());
		return ioPorts;
	}
	
	public List<I2cBus> getI2cBuses() {
		return i2cBuses;
	}
	
	public List<SerialPort> getSerialPorts() {
		return serialBuses;
	}
	
	public SerialPort getSerialPort(String name) {
		return (SerialPort)this.getIOPortByName(name);
	}
	
	public I2cBus getI2cBus(String name) {
		return (I2cBus)this.getIOPortByName(name);
	}
	
	public boolean hasProperty(String propertyName) {
		return properties.containsKey(propertyName);
	}
	
	public String getProperty(String propertyName) {
		return properties.getProperty(propertyName);
	}
	
	public Properties getProperties() {
		return properties;
	}
	
	public void setProperty(String propertyName, String value) {
		this.properties.put(propertyName, value);
	}
	
	public abstract String getName();
}
