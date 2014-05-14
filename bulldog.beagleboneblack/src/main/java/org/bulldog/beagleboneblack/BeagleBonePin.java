package org.bulldog.beagleboneblack;

import org.bulldog.core.gpio.Pin;

public class BeagleBonePin extends Pin {

	private int bank;
	private int pinIndex;
	private int port;
	private int indexOnPort;
	private String am335xName;
	
	public BeagleBonePin(String name, String am335xName, int bank, int pinIndex, int port, int indexOnPort) {
		super(name, 32 * bank + pinIndex);
		this.port = port;
		this.indexOnPort = indexOnPort;
		this.am335xName = am335xName;
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
	
	public String getAm335xName() {
		return am335xName;
	}
	
}
