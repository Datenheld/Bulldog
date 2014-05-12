package org.bulldog.bulldog.beagleboneblack;

import org.bulldog.core.gpio.Pin;

public class BeagleBonePin extends Pin {

	private int bank;
	private int pinIndex;
	private int port;
	private int indexOnPort;
	
	public BeagleBonePin(String name, String internalName, int bank, int pinIndex, int port, int indexOnPort) {
		super(name, internalName, 32 * bank + pinIndex);
		this.port = port;
		this.indexOnPort = indexOnPort;
	}
	
	public int getBank() {
		return bank;
	}
	
	public int getPinIndex() {
		return pinIndex;
	}
	
	public int getPort() {
		return port;
	}
	
	public int getIndexOnPort() {
		return indexOnPort;
	}
	
}
