package org.bulldog.core.io.uart;

import org.bulldog.core.gpio.PinFeature;

public interface UartPinFeature extends PinFeature {

	public UartPort getPort();
}
