package org.bulldog.core.io.serial;

public interface SerialDataListener {

	void onSerialDataAvailable(SerialDataEventArgs args);
	
}
