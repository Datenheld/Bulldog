package org.bulldog.core;

import java.util.ArrayList;
import java.util.List;

import org.bulldog.core.bus.I2cBus;
import org.bulldog.core.gpio.Pin;

public abstract class AbstractBoard implements Board {

	private List<Pin> pins = new ArrayList<Pin>();
	private List<I2cBus> i2cBuses = new ArrayList<I2cBus>();

	public List<Pin> getPins() {
		return pins;
	}

	public Pin getPinByAddress(int address) {
		for(Pin pin : getPins()) {
			if(pin.getAddress() == address) {
				return pin;
			}
		}
		
		return null;
	}

	public Pin getPinByName(String name) {
		if(name == null) 	{ throw new IllegalArgumentException("Null may not be passed as a name for a pin."); }
		if("".equals(name)) { throw new IllegalArgumentException("Searching for empty pin names is not permitted."); }
		
		for(Pin pin : getPins()) {
			if(name.equals(pin.getName())) {
				return pin;
			}
		}
		
		return null;
	}
	
	public Pin getPinByInternalName(String name) {
		if(name == null) 	{ throw new IllegalArgumentException("Null may not be passed as an internal name for a pin."); }
		if("".equals(name)) { throw new IllegalArgumentException("Searching for empty internal names is not permitted."); }
		
		for(Pin pin : getPins()) {
			if(name.equals(pin.getInternalName())) {
				return pin;
			}
		}
		
		return null;
	}
	
	public Pin getPinByAlias(String alias) {
		if(alias == null) 	{ throw new IllegalArgumentException("Null may not be passed as an alias name for a pin."); }
		if("".equals(alias)) { throw new IllegalArgumentException("Searching for empty alias names is not permitted."); }
		
		for(Pin pin : getPins()) {
			if(alias.equals(pin.getAlias())) {
				return pin;
			}
		}
		
		return null;
	}
	
	
	public abstract String getName();
	
	public List<I2cBus> getI2cBuses() {
		return i2cBuses;
	}

}
