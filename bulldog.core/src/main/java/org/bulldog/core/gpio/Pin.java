package org.bulldog.core.gpio;

import java.util.ArrayList;
import java.util.List;

import org.bulldog.core.gpio.event.FeatureActivationEventArgs;
import org.bulldog.core.gpio.event.FeatureActivationListener;

public class Pin {
	
	private List<PinFeature> features = new ArrayList<PinFeature>();
	private int address = 0;
	private String name = null;
	private String alias = null;
	private String port = null;
	private int indexOnPort = 0;
	private PinFeature activeFeature = null;
	private PinFeature blocker = null;
	private List<FeatureActivationListener> activationListeners = new ArrayList<FeatureActivationListener>();

	public Pin(String name, int address, String port, int indexOnPort) {
		this.name = name;
		this.address = address;
		this.port = port;
		this.indexOnPort = indexOnPort;
	}
	
	public <T extends PinFeature> void activateFeature(Class<T> feature) {
		checkIfDesiredFeatureIsAvailable(feature);
		T selectedFeature = (T)getFeature(feature);	
		activateFeature(selectedFeature);
	}
	
	public Pin addFeature(PinFeature feature) {
		for(PinFeature feat : getFeatures()) {
			if(feat.getClass().equals(feature.getClass())) {
				throw new IllegalArgumentException("This pin already posseses the feature you are trying to add!");
			}
		}
		
		getFeatures().add(feature);
		return this;
	}
	
	public Pin removeFeature(Class<? extends PinFeature> featureClass) {
		PinFeature featureInstance = null;
		for(PinFeature feature : getFeatures()) {
			if(feature.getClass().equals(featureClass)) {
				featureInstance = feature;
				break;
			}
		}
		
		if(featureInstance != null) {
			featureInstance.teardown();
			if(featureInstance == getActiveFeature()) {
				setActiveFeature(null);
			}
			getFeatures().remove(featureInstance);
		}
		
		return this;
	}
	
	public <T extends PinFeature> T as(Class<T> feature) {
		checkIfDesiredFeatureIsAvailable(feature);
		T selectedFeature = getFeature(feature);	
		activateFeature(selectedFeature);
		return selectedFeature;
	}
	
	public void block(PinFeature blocker) {
		if(getBlocker() != null && getBlocker() != blocker) { throw new PinBlockedException(getBlocker()); }
		this.blocker = blocker;
	}
	
	public void unblock(PinFeature blocker) {
		if(getBlocker() != null && getBlocker() != blocker) { throw new PinBlockedException(getBlocker()); }
		this.blocker = null;
	}
	
	public PinFeature getBlocker() {
		return blocker;
	}
	
	public boolean isBlocked() {
		return blocker != null;
	}

	@SuppressWarnings("unchecked")
	public <T extends PinFeature> T getFeature(Class<T> featureClass) {
		PinFeature foundFeature = null;
		for(PinFeature feature : getFeatures()) {
			if(feature.getClass().equals(featureClass)) {
				foundFeature = feature;
				break;
			} else if(foundFeature == null && featureClass.isAssignableFrom(feature.getClass())) {
				foundFeature = feature;
			}
		}
		
		return (T)foundFeature;
	}

	public PinFeature getActiveFeature() {
		return activeFeature;
	}
	
	public boolean isFeatureActive(Class<? extends PinFeature> featureClass) {
		if(getActiveFeature() == null) { return false; }
		return featureClass.isAssignableFrom(getActiveFeature().getClass());
	}
	
	public boolean isFeatureActive(PinFeature feature) {
		return getActiveFeature() == feature;
	}
	
	public int getAddress() {
		return address;
	}
	
	public String getPort() {
		return port;
	}
	
	public int getIndexOnPort() {
		return indexOnPort;
	}
	
	public List<PinFeature> getFeatures() {
		return features;
	}
	
	public String getName() {
		return name;
	}
	
	public String getAlias() {
		return alias;
	}
	
	public void setAlias(String alias) {
		this.alias = alias;
	}
	
	public boolean hasFeature(Class<? extends PinFeature> featureClass) {
		for(PinFeature feature : getFeatures()) {
			if(featureClass.isAssignableFrom(feature.getClass())) {
				return true;
			}
		}
		
		return false;
	}
	
	private void setActiveFeature(PinFeature feature) {
		activeFeature = feature;
	}
	
	private void activateFeature(PinFeature feature) {
		if(feature == getActiveFeature()) { return; }
		
		PinFeature blocker = getBlocker();
		if(blocker != null) {
			throw new PinBlockedException(blocker);
		}
		
		if(getActiveFeature() != null) {
			deactivateFeature(getActiveFeature());
		}
		
		fireFeatureActivating(feature);
		feature.setup();
		setActiveFeature(feature);
		fireFeatureActivated(feature);
	}
	
	private void deactivateFeature(PinFeature feature) {
		fireFeatureDeactivating(feature);
		feature.teardown();
		fireFeatureDeactivated(feature);
	}
	
	private <T extends PinFeature> void checkIfDesiredFeatureIsAvailable(Class<T> feature) {
		if(!hasFeature(feature)) { throw new IllegalArgumentException("This pin does not possess the desired feature"); }
	}
	
	public void addFeatureActivationListener(FeatureActivationListener listener) {
		activationListeners.add(listener);
	}
	
	public void removeFeatureActivationListener(FeatureActivationListener listener) {
		activationListeners.remove(listener);
	}
	
	public void clearFeatureActivationListeners() {
		activationListeners.clear();
	}
	
	public List<FeatureActivationListener> getFeatureActivationListeners() {
		return activationListeners;
	}
	
	protected void fireFeatureActivating(PinFeature feature) {
		for(FeatureActivationListener listener : activationListeners) {
			listener.featureActivating(this, new FeatureActivationEventArgs(feature));
		}
	}
	
	protected void fireFeatureActivated(PinFeature feature) {
		for(FeatureActivationListener listener : activationListeners) {
			listener.featureActivated(this, new FeatureActivationEventArgs(feature));
		}
	}
	
	protected void fireFeatureDeactivating(PinFeature feature) {
		for(FeatureActivationListener listener : activationListeners) {
			listener.featureDeactivating(this, new FeatureActivationEventArgs(feature));
		}
	}
	
	protected void fireFeatureDeactivated(PinFeature feature) {
		for(FeatureActivationListener listener : activationListeners) {
			listener.featureDeactivated(this, new FeatureActivationEventArgs(feature));
		}
	}
}
