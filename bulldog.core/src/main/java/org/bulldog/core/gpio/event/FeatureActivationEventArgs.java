package org.bulldog.core.gpio.event;

import org.bulldog.core.gpio.PinFeature;

public class FeatureActivationEventArgs {

	private PinFeature feature;
	
	public FeatureActivationEventArgs(PinFeature feature) {
		this.feature = feature;
	}
	
	public PinFeature getPinFeature() {
		return this.feature;
	}
	
}
