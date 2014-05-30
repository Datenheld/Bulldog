package org.bulldog.core.gpio;



public interface PinFeature {

	String getName();
	Pin getPin();
	
	boolean isActive();
	
	boolean isBlocking();
	void setBlocking(boolean blocking);
	
	void setup();
	void teardown();
}
