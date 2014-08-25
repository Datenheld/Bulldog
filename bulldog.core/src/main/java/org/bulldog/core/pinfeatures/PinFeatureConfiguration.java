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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((desiredFeature == null) ? 0 : desiredFeature.hashCode());
		result = prime
				* result
				+ ((featureProperties == null) ? 0 : featureProperties
						.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PinFeatureConfiguration other = (PinFeatureConfiguration) obj;
		if (desiredFeature == null) {
			if (other.desiredFeature != null)
				return false;
		} else if (!desiredFeature.equals(other.desiredFeature))
			return false;
		if (featureProperties == null) {
			if (other.featureProperties != null)
				return false;
		} else if (!featureProperties.equals(other.featureProperties))
			return false;
		return true;
	}
	
}
