package org.bulldog.core.pinfeatures;

public class FeatureFinder {

	public static PinFeature findFeature(PinFeatureProvider provider, Class<? extends PinFeature> featureClass) {
		PinFeature foundFeature = null;
		for(PinFeature feature : provider.getFeatures()) {
			
			if(feature.getClass().equals(featureClass)) {
				return feature;
			} else if(feature instanceof PinFeatureProvider) {
				PinFeatureProvider childProvider = (PinFeatureProvider)feature;
				if(childProvider.hasFeature(featureClass)) {
					foundFeature = findFeature(childProvider, featureClass);
					if(foundFeature != null && foundFeature.equals(featureClass)) {
						return feature;
					}
				}
			} 
			
			if(featureClass.isAssignableFrom(feature.getClass())) {
				foundFeature = feature;
			}
		}
		
		return foundFeature;
	}
	
}
