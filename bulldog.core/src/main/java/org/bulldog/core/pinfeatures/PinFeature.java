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
	boolean isActiveOnPin();
	boolean isActiveAs(Class<? extends PinFeature> featureClass);
	
	void setup(PinFeatureConfiguration configuration);
	void teardown();
	
	PinFeatureConfiguration getConfiguration();
}
