package org.bulldog.core.platform;

import java.util.ArrayList;
import java.util.List;

import org.bulldog.core.gpio.Pin;

public class AbstractPinProvider implements PinProvider {

	private List<Pin> pins = new ArrayList<Pin>();
	
	public List<Pin> getPins() {
		return pins;
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
		
	
}
