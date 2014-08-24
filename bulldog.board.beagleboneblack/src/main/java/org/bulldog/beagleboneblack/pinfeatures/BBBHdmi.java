package org.bulldog.beagleboneblack.pinfeatures;

import org.bulldog.core.pinfeatures.Pin;
import org.bulldog.core.pinfeatures.PinFeatureConfiguration;
import org.bulldog.core.pinfeatures.base.AbstractPinFeature;

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
	protected void setupImpl(PinFeatureConfiguration configuration) {
		blockPin();
	}

	@Override
	protected void teardownImpl() {
	}

}
