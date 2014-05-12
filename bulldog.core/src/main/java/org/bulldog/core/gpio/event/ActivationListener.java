package org.bulldog.core.gpio.event;

public interface ActivationListener {

	void featureActivating(Object o, ActivationEventArgs args);
	void featureActivated(Object o, ActivationEventArgs args);
	void featureDeactivating(Object o, ActivationEventArgs args);
	void featureDeactivated(Object o, ActivationEventArgs args);
	
}
