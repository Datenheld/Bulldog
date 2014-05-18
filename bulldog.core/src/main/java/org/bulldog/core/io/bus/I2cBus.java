package org.bulldog.core.io.bus;

import org.bulldog.core.gpio.Pin;

public interface I2cBus extends Bus {
	
	public Pin getSDA();
	public Pin getSCL();
	
}
