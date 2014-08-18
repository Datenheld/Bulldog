package org.bulldog.core.platform;

import java.util.List;

import org.bulldog.core.gpio.Pin;

public interface PinProvider {

	List<Pin> getPins();
	Pin getPinByAlias(String alias);
	Pin getPin(int address);
	Pin getPin(String name);
	Pin getPin(String port, int indexOnPort);
	
}
