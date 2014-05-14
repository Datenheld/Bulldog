package org.bulldog.core.gpio;

import java.util.ArrayList;
import java.util.List;

import org.bulldog.core.gpio.event.ActivationEventArgs;
import org.bulldog.core.gpio.event.ActivationListener;

public class Pin {
	
	private List<PinFeature> features = new ArrayList<PinFeature>();
	private int address = 0;
	private String name = "";
	private String alias = "";
	private PinFeature activeFeature = null;
	private List<ActivationListener> activationListeners = new ArrayList<ActivationListener>();

	public Pin(String name, int address) {
		this.name = name;
		this.address = address;
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
	
	public PinFeature getBlocker() {
		for(PinFeature feature : getFeatures()) {
			if(feature.isBlocking()) {
				return feature;
			}
		} 
		
		return null;
	}
	
	public boolean isBlocked() {
		for(PinFeature feature : getFeatures()) {
			if(feature.isBlocking()) {
				return true;
			}
		}
		
		return false;
	}
	
	private <T extends PinFeature> void checkIfDesiredFeatureIsAvailable(Class<T> feature) {
		if(!hasFeature(feature)) { throw new IllegalArgumentException("This pin does not possess the desired feature"); }
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
	
	public int getAddress() {
		return address;
	}
	
	public List<PinFeature> getFeatures() {
		return features;
	}
	
	public String getName() {
		return name;
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
	
	public void addActivationListener(ActivationListener listener) {
		activationListeners.add(listener);
	}
	
	public void removeActivationListener(ActivationListener listener) {
		activationListeners.remove(listener);
	}
	
	public void clearActivationListeners(ActivationListener listener) {
		activationListeners.clear();
	}
	
	protected void fireFeatureActivating(PinFeature feature) {
		for(ActivationListener listener : activationListeners) {
			listener.featureActivating(this, new ActivationEventArgs(feature));
		}
	}
	
	protected void fireFeatureActivated(PinFeature feature) {
		for(ActivationListener listener : activationListeners) {
			listener.featureActivated(this, new ActivationEventArgs(feature));
		}
	}
	
	protected void fireFeatureDeactivating(PinFeature feature) {
		for(ActivationListener listener : activationListeners) {
			listener.featureDeactivating(this, new ActivationEventArgs(feature));
		}
	}
	
	protected void fireFeatureDeactivated(PinFeature feature) {
		for(ActivationListener listener : activationListeners) {
			listener.featureDeactivated(this, new ActivationEventArgs(feature));
		}
	}
	
	public String getAlias() {
		return alias;
	}
	
	public void setAlias(String alias) {
		this.alias = alias;
	}
}
