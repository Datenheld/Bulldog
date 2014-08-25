package org.bulldog.core.io.uart;

import org.bulldog.core.gpio.Pin;
import org.bulldog.core.io.serial.SerialPort;

public interface UartPort extends SerialPort {

	public Pin getRx();
	public Pin getTx();
	
}
