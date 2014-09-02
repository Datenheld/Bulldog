package org.bulldog.core.pinfeatures.base;

import org.bulldog.core.pinfeatures.Pin;
import org.bulldog.core.pinfeatures.PinFeature;
import org.bulldog.core.pinfeatures.PinFeatureConfiguration;

public abstract class AbstractPinFeature implements PinFeature {

	private Pin pin;
	private boolean isSetup = false;
	private boolean isTornDownOnShutdown = false;
	private PinFeatureConfiguration configuration;
	
	public AbstractPinFeature(Pin pin) {
		this.pin = pin;
	}

	public Pin getPin() {
		return pin;
	}
	
	public boolean isActivatedFeature() {
		return this.getPin().getActiveFeature() == this;
	}
	
	public void activate() {
		getPin().activateFeature(getClass());
	}
	
	public void blockPin() {
		pin.block(this);
	}
	
	public boolean isBlocking() {
		return pin.getBlocker() == this;
	}
	
	public void unblockPin() {
		getPin().unblock(this);
	}
	
	protected abstract void setupImpl(PinFeatureConfiguration configuration);
	protected abstract void teardownImpl();
	
	public void setup(PinFeatureConfiguration configuration) {
		this.configuration = configuration;
		setupImpl(configuration);
		isSetup = true;
	}
	
	public void teardown() {
		teardownImpl();
		isSetup = false;
	}
	
	public boolean isSetup() {
		return isSetup;
	}
	
	public PinFeatureConfiguration getConfiguration() {
		return this.configuration;
	}
	
	@Override
	public boolean isActiveAs(Class<? extends PinFeature> featureClass) {
		return (featureClass.isAssignableFrom(getConfiguration().getDesiredFeature())
				&& pin.getActiveFeature() == this);
	}
	
	public boolean isTornDownOnShutdown() {
		return this.isTornDownOnShutdown;
	}
	
	public void setTeardownOnShutdown(boolean teardown) {
		this.isTornDownOnShutdown = true;
	}
	
	@Override
	public String toString() {
		String string =  this.getName();
		if(string == null) {
			string = super.toString();
		}
		
		return string;
	}
}
