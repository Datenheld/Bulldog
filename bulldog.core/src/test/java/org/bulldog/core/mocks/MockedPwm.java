package org.bulldog.core.mocks;

import org.bulldog.core.gpio.Pin;
import org.bulldog.core.gpio.base.AbstractPwm;

public class MockedPwm extends AbstractPwm {

	private boolean enableImplCalled = false;
	private boolean disableImplCalled = false;
	private boolean pwmImplCalled = false;
	
	public MockedPwm(Pin pin) {
		super(pin);
	}

	@Override
	public void setup() {
	}

	@Override
	public void teardown() {
	}

	@Override
	protected void setPwmImpl(float frequency, float duty) {
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
