package org.bulldog.core.io.bus.spi;

public enum SpiMode {
	Mode0(0),
	Mode1(1),
	Mode2(2),
	Mode3(3);
	
	private int modeValue;
	
	private SpiMode(int modeValue) {
		this.modeValue = modeValue;
	}
	
	public int getNumericValue() {
		return modeValue;
	}
}
