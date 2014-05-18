package org.bulldog.core.platform;

import java.util.ArrayList;
import java.util.List;

import org.bulldog.core.gpio.Pin;
import org.bulldog.core.io.IOPort;
import org.bulldog.core.io.SerialIO;
import org.bulldog.core.io.bus.I2cBus;

public abstract class AbstractBoard implements Board {

	private List<Pin> pins = new ArrayList<Pin>();
	private List<I2cBus> i2cBuses = new ArrayList<I2cBus>();
	private List<SerialIO> serialBuses = new ArrayList<SerialIO>();

	public List<Pin> getPins() {
		return  pins;
	}

	public Pin getPinByAddress(int address) {
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

	public Pin getPinByName(String name) {
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
	
	public List<I2cBus> getI2cBuses() {
		return i2cBuses;
	}
	
	public List<SerialIO> getSerialPorts() {
		return serialBuses;
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
	
	public abstract String getName();
}
