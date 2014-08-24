package org.bulldog.core;

import org.bulldog.core.util.BulldogUtil;

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
	
	public static Signal fromString(String value) {
		if(value == null) { throw new IllegalArgumentException("value cannot be null!"); };

		String interpretedValue = value.trim().toLowerCase();
		
		if(BulldogUtil.isStringNumeric(interpretedValue)) {
			if(Double.parseDouble(interpretedValue) == 0) { return Signal.Low; }
			return Signal.High;
		}  else {
			if(interpretedValue.equals("low")) { return Signal.Low; }
			else if(interpretedValue.equals("high")) { return Signal.High; };
			
			throw new IllegalArgumentException(interpretedValue + " is not a valid value for a signal");
		}
	}
	
	public Signal inverse() {
		return this == Signal.High ? Signal.Low : Signal.High;
	}
}
