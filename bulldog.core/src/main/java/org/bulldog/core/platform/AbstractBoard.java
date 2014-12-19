package org.bulldog.core.platform;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.bulldog.core.gpio.Pin;
import org.bulldog.core.io.IOPort;
import org.bulldog.core.io.bus.i2c.I2cBus;
import org.bulldog.core.io.bus.spi.SpiBus;
import org.bulldog.core.io.serial.SerialPort;

public abstract class AbstractBoard extends AbstractPinProvider implements Board {


	private List<I2cBus> i2cBuses = new ArrayList<I2cBus>();
	private List<SpiBus> spiBuses = new ArrayList<SpiBus>();
	private List<SerialPort> serialBuses = new ArrayList<SerialPort>();
	private Properties properties = new Properties();

	public AbstractBoard() {
		createShutdownHook();
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
		ioPorts.addAll(getSpiBuses());
		return ioPorts;
	}
	
	public List<I2cBus> getI2cBuses() {
		return i2cBuses;
	}
	
	public List<SerialPort> getSerialPorts() {
		return serialBuses;
	}
	
	public List<SpiBus> getSpiBuses() {
		return spiBuses;
	}
	
	public SpiBus getSpiBus(String name) {
		return (SpiBus)this.getIOPortByName(name);
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
	
	public void shutdown() {
		cleanup();
	}
	
	public void cleanup() {
		for(Pin pin : this.getPins()) {
			if(pin.getActiveFeature() == null) { continue; }
			if(pin.getActiveFeature().isTorndownOnShutdown()) {
				pin.getActiveFeature().teardown();
			}
		}
	}
	
	public void createShutdownHook() {
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				cleanup();
			}
		});
	}
}
