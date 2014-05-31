package org.bulldog.core;

public enum Signal {
	High(1, true),
	Low(0, false);
	
	private int numericValue;
	private boolean booleanValue;
	
	Signal(int numericValue, boolean booleanValue) {
		this.numericValue = numericValue;
		this.booleanValue = booleanValue;
	}

	public int getNumericValue() {
		return numericValue;
	}
	
	public boolean getBooleanValue() {
		return booleanValue;
	}
	
	public static Signal fromNumericValue(int value) {
		if(value == 0) { return Signal.Low; }
		return Signal.High;
	}
	
	public static Signal fromBooleanValue(boolean value) {
		if(value) { return Signal.High; }
		return Signal.Low;
	}
}
