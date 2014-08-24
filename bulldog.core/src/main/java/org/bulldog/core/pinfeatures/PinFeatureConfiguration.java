package org.bulldog.core.pinfeatures;

import java.util.Properties;

public class PinFeatureConfiguration {

	private Class<? extends PinFeature> desiredFeature;
	private Properties featureProperties;
	
	public PinFeatureConfiguration(Class<? extends PinFeature> featureClass) {
		this.desiredFeature = featureClass;
	}
	
	public Class<? extends PinFeature> getDesiredFeature() {
		return desiredFeature;
	}
	
	public void setDesiredFeature(Class<? extends PinFeature> desiredFeature) {
		this.desiredFeature = desiredFeature;
	}
	
	public Properties getFeatureProperties() {
		return featureProperties;
	}
	
	public void setFeatureProperties(Properties featureProperties) {
		this.featureProperties = featureProperties;
	}
	
}
