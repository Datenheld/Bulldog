package org.bulldog.core.mocks;

import org.bulldog.core.pinfeatures.Pin;
import org.bulldog.core.pinfeatures.PinFeatureConfiguration;
import org.bulldog.core.pinfeatures.base.AbstractPwm;

public class MockedPwm extends AbstractPwm {

	private boolean enableImplCalled = false;
	private boolean disableImplCalled = false;
	private boolean pwmImplCalled = false;
	
	public MockedPwm(Pin pin) {
		super(pin);
	}

	@Override
	protected void setupImpl(PinFeatureConfiguration configuration) {
	}

	@Override
	protected void teardownImpl() {
	}

	@Override
	protected void setPwmImpl(double frequency, double duty) {
		pwmImplCalled = true;
	}
	
	public boolean pwmImplCalled() {
		return pwmImplCalled;
	}

	@Override
	protected void enableImpl() {
		enableImplCalled = true;
	}
	
	public boolean enableImplCalled() {
		return enableImplCalled;
	}

	@Override
	protected void disableImpl() {
		disableImplCalled = true;
	}
	
	public boolean disableImplCalled() {
		return disableImplCalled;
	}
	
	public void reset() {
		disableImplCalled = false;
		enableImplCalled = false;
		pwmImplCalled = false;
	}
	


}
