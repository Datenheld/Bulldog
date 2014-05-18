package org.bulldog.core.bus;

import org.bulldog.core.Parity;

public interface SerialBus extends Bus {
	
	int getBaudRate();
	void setBaudRate(int baudRate);
	
	Parity getParity();
	void setParity(Parity parity);
}
