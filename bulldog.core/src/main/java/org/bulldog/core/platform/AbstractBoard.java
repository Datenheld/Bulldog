package org.bulldog.core.platform;

import java.util.ArrayList;
import java.util.List;

import org.bulldog.core.bus.Bus;
import org.bulldog.core.bus.I2cBus;
import org.bulldog.core.bus.SerialBus;
import org.bulldog.core.gpio.Pin;

public abstract class AbstractBoard implements Board {

	private List<Pin> pins = new ArrayList<Pin>();
	private List<I2cBus> i2cBuses = new ArrayList<I2cBus>();
	private List<SerialBus> serialBuses = new ArrayList<SerialBus>();

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
	
	public List<SerialBus> getSerialBuses() {
		return serialBuses;
	}
	
	public Bus getBusByAlias(String alias) {
		if(alias == null) 	{ throw new IllegalArgumentException("Null may not be passed as an alias name for a bus."); }
	
		for(Bus bus : getAllBuses()) {
			if(alias.equals(bus.getAlias())) {
				return bus;
			}
		}
		
		return null;
	}
	
	public Bus getBusByName(String name) {
		if(name == null) 	{ throw new IllegalArgumentException("Null may not be passed as a name for a bus."); }
	
		for(Bus bus : getAllBuses()) {
			if(name.equals(bus.getName())) {
				return bus;
			}
		}
		
		return null;
	}

	public List<Bus> getAllBuses() {
		List<Bus> buses = new ArrayList<Bus>();
		buses.addAll(getI2cBuses());
		buses.addAll(getSerialBuses());
		return buses;
	}
	
	public abstract String getName();
}
