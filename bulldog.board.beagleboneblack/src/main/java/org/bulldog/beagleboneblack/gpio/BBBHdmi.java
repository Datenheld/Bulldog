package org.bulldog.beagleboneblack.gpio;

import org.bulldog.core.gpio.Pin;
import org.bulldog.core.gpio.base.AbstractPinFeature;

public class BBBHdmi extends AbstractPinFeature {

	private static final String NAME_FORMAT = "HDMI on Pin %s - (you need to disable this feature at boot time)";
		
	public BBBHdmi(Pin pin) {
		super(pin);
	}

	@Override
	public String getName() {
		return String.format(NAME_FORMAT, getPin().getName());
	}

	@Override
	protected void setupImpl() {
		blockPin();
	}

	@Override
	protected void teardownImpl() {
	}

}
