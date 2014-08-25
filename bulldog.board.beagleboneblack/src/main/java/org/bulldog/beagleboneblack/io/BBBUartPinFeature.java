package org.bulldog.beagleboneblack.io;

import org.bulldog.core.gpio.Pin;
import org.bulldog.core.io.uart.AbstractUartPinFeature;
import org.bulldog.core.io.uart.UartPort;
import org.bulldog.core.io.uart.UartSignalType;

public class BBBUartPinFeature extends AbstractUartPinFeature {

	private BBBUartPort port;
	
	public BBBUartPinFeature(BBBUartPort port, Pin pin, UartSignalType signalType) {
		super(pin, signalType);
		this.port = port;
	}

	@Override
	protected void setupImpl() {
		port.setup();
		blockPin();
	}

	@Override
	protected void teardownImpl() {
		port.teardown();
	}

	@Override
	public UartPort getPort() {
		return port;
	}

}
