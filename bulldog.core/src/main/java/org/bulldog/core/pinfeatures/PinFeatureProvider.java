package org.bulldog.core.pinfeatures;

import java.util.List;

public interface PinFeatureProvider {

	<T extends PinFeature> T getFeature(Class<T> featureClass);
	boolean hasFeature(Class<? extends PinFeature> featureClass);
	List<PinFeature> getFeatures();
	
}
