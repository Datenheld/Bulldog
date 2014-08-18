package org.bulldog.core.gpio;

public class PinBlockedException extends RuntimeException {

	private static final long serialVersionUID = 6737984685844582750L;
	
	private PinFeature blocker;
	
	public PinBlockedException(PinFeature blocker) {
		super(String.format("Pin %s is currently blocked by %s", blocker.getPin().getName(), blocker.getName()));
		this.blocker = blocker;
	}
	
	public PinFeature getBlocker() {
		return blocker;
	}

}
