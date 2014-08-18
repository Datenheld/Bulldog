package org.bulldog.core.gpio;

public interface DigitalIO extends DigitalInput, DigitalOutput {

	boolean isInputActive();
	boolean isOutputActive();
	
}
