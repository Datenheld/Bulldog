package org.bulldog.beagleboneblack.pinfeatures;

import org.bulldog.beagleboneblack.BeagleBonePin;
import org.bulldog.beagleboneblack.jni.NativeGpio;
import org.bulldog.core.Signal;
import org.bulldog.core.pinfeatures.Pin;
import org.bulldog.core.pinfeatures.PinFeatureConfiguration;
import org.bulldog.core.pinfeatures.base.AbstractDigitalOutput;

public class BBBDigitalOutput extends AbstractDigitalOutput {

	public BBBDigitalOutput(Pin pin) {
		super(pin);
	}

	protected void setupImpl(PinFeatureConfiguration configuration) {
		BeagleBonePin bbbPin = (BeagleBonePin)getPin();
		NativeGpio.pinMode(bbbPin.getPortNumeric(), bbbPin.getIndexOnPort(), NativeGpio.DIRECTION_OUT);
		applySignal(getAppliedSignal());
	}

	protected void teardownImpl() {
	}

	@Override
	protected void applySignalImpl(Signal signal) {
		BeagleBonePin bbbPin = (BeagleBonePin)getPin();
		NativeGpio.digitalWrite(bbbPin.getPortNumeric(), bbbPin.getIndexOnPort(), signal == Signal.High ? NativeGpio.HIGH : NativeGpio.LOW);
	}

}
