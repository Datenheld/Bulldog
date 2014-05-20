package org.bulldog.core.io.bus.i2c;

import org.bulldog.core.gpio.Pin;
import org.bulldog.core.io.bus.Bus;

public interface I2cBus extends Bus {
	
	public Pin getSDA();
	public Pin getSCL();
	public int getFrequency();
	
}
