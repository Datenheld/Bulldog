package org.bulldog.core.pinfeatures.event;

import org.bulldog.core.pinfeatures.PinFeature;

public class FeatureActivationEventArgs {

	private PinFeature feature;
	
	public FeatureActivationEventArgs(PinFeature feature) {
		this.feature = feature;
	}
	
	public PinFeature getPinFeature() {
		return this.feature;
	}
	
}
