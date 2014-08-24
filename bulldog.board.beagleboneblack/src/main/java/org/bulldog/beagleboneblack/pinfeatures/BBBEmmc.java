package org.bulldog.beagleboneblack.pinfeatures;

import org.bulldog.core.pinfeatures.Pin;
import org.bulldog.core.pinfeatures.PinFeatureConfiguration;
import org.bulldog.core.pinfeatures.base.AbstractPinFeature;

public class BBBEmmc extends AbstractPinFeature {

	private static final String NAME_FORMAT = "EMMC on Pin %s - (you need to disable this feature at boot time)";
	
	public BBBEmmc(Pin pin) {
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
