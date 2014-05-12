package org.bulldog.core.gpio.event;

import org.bulldog.core.gpio.PinFeature;

public class ActivationEventArgs {

	private PinFeature feature;
	
	public ActivationEventArgs(PinFeature feature) {
		this.feature = feature;
	}
	
	public PinFeature getPinFeature() {
		return this.feature;
	}
	
}
