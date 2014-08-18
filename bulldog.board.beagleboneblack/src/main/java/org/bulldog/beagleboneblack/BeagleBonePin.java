package org.bulldog.beagleboneblack;

import org.bulldog.core.gpio.Pin;

public class BeagleBonePin extends Pin {

	private int bank;
	private int pinIndex;
	private String am335xName;
	
	public BeagleBonePin(String name, String am335xName, int bank, int pinIndex, String port, int indexOnPort) {
		super(name, 32 * bank + pinIndex, port, indexOnPort);
		this.am335xName = am335xName;
	}
	
	public int getBank() {
		return bank;
	}
	
	public int getPinIndex() {
		return pinIndex;
	}
	
	public int getPortNumeric() {
		int portNumber = Integer.parseInt(getPort().substring(1));
		return portNumber;
	}
	
	public String getAm335xName() {
		return am335xName;
	}
	
}
