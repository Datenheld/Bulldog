package org.bulldog.core.pinfeatures;



public interface PinFeature {

	String getName();
	Pin getPin();
	
	boolean isActivatedFeature();
	void activate();
	
	boolean isBlocking();
	void blockPin();
	void unblockPin();
	
	boolean isSetup();
	boolean isActiveAs(Class<? extends PinFeature> featureClass);
	
	void setup(PinFeatureConfiguration configuration);
	void teardown();
	boolean isTornDownOnShutdown();
	void setTeardownOnShutdown(boolean teardown);
	
	PinFeatureConfiguration getConfiguration();
}
