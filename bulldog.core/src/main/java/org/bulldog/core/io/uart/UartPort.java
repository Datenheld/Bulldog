package org.bulldog.core.io.uart;

import org.bulldog.core.io.serial.SerialPort;
import org.bulldog.core.pinfeatures.Pin;

public interface UartPort extends SerialPort {

	public Pin getRx();
	public Pin getTx();
	
}
