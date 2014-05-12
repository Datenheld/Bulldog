package org.bulldog.core.gpio;



public interface PinFeature {

	String getName();
	Pin getPin();
	
	boolean isActive();
	boolean isBlocking();
	void setup();
	void teardown();
}
