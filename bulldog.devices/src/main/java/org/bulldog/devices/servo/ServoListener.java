package org.bulldog.devices.servo;

public interface ServoListener {

	void angleChanged(Servo servo, float oldAngle, float newAngle);
	
}
