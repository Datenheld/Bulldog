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
		if(value == null) { return Signal.Low; }

		if(BulldogUtil.isStringNumeric(value)) {
			if(Integer.parseInt(value) == 0) { return Signal.Low; }
			return Signal.High;
		}  else {
			if(value.toLowerCase().equals("low")) { return Signal.Low; }
			return Signal.High;
		}
	}
	
	public Signal inverse() {
		return this == Signal.High ? Signal.Low : Signal.High;
	}
}
