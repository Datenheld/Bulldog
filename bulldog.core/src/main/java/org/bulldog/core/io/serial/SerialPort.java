package org.bulldog.core.io.serial;

import org.bulldog.core.Parity;
import org.bulldog.core.io.IOPort;

public interface SerialPort extends IOPort {
	
	int getBaudRate();
	void setBaudRate(int baudRate);
	
	Parity getParity();
	void setParity(Parity parity);
	
	int getDataBits();
	void setDataBits(int dataBits);
	
	int getStopBits();
	void setStopBits(int stopBits);
	
	void setBlocking(boolean blocking);
	boolean getBlocking();
	
	void addListener(SerialDataListener listener);
	void removeListener(SerialDataListener listener);
}
