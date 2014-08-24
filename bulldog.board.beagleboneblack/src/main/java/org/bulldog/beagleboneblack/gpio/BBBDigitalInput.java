package org.bulldog.beagleboneblack.gpio;

import org.bulldog.beagleboneblack.BeagleBonePin;
import org.bulldog.beagleboneblack.jni.NativeGpio;
import org.bulldog.core.Signal;
import org.bulldog.core.gpio.Pin;
import org.bulldog.core.gpio.PinFeatureConfiguration;
import org.bulldog.linux.gpio.LinuxDigitalInput;

public class BBBDigitalInput extends LinuxDigitalInput {

	public BBBDigitalInput(Pin pin) {
		super(pin);
	}
	
	public Signal read() {
		BeagleBonePin bbbPin = (BeagleBonePin)getPin();
		return Signal.fromNumericValue(NativeGpio.digitalRead(bbbPin.getPortNumeric(), bbbPin.getIndexOnPort()));
	}

	public void setup(PinFeatureConfiguration configuration) {
		super.setup(configuration);
		BeagleBonePin bbbPin = (BeagleBonePin)getPin();
		NativeGpio.pinMode(bbbPin.getPortNumeric(), bbbPin.getIndexOnPort(), NativeGpio.DIRECTION_IN);
	}
	
}
