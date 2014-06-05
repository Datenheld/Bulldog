package org.bulldog.core.gpio;



public interface PinFeature {

	String getName();
	Pin getPin();
	
	boolean isActivatedFeature();
	
	boolean isBlocking();
	void blockPin();
	void unblockPin();
	
	void setup();
	void teardown();
}
