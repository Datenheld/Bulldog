package org.bulldog.core.gpio;



public interface PinFeature {

	String getName();
	Pin getPin();
	
	boolean isActivatedFeature();
	
	boolean isBlocking();
	void blockPin();
	void unblockPin();
	
	boolean isSetup();
	
	void setup();
	void teardown();
}
