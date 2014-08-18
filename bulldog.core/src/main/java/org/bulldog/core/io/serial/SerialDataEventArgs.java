package org.bulldog.core.io.serial;

import org.bulldog.core.util.BulldogUtil;

public class SerialDataEventArgs {

	private byte[] data;
	private SerialPort port;
	
	public SerialDataEventArgs(SerialPort port, byte[] data) {
		this.port = port;
		this.data = data;
	}

	public byte[] getData() {
		return data;
	}

	public SerialPort getPort() {
		return port;
	}
	
	public String getDataAsString() {
		return BulldogUtil.bytesToString(getData());
	}
}
