package org.bulldog.core.gpio;



public interface PinFeature {

	String getName();
	Pin getPin();
	
	boolean isActivatedFeature();
	
	boolean isBlocking();
	void setBlocking(boolean blocking);
	
	void setup();
	void teardown();
}
