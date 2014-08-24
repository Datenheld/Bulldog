package org.bulldog.devices.servo;

public interface ServoListener {

	void angleChanged(Servo servo, double oldAngle, double newAngle);
	void moveCompleted(Servo servo, double oldAngle, double newAngle);
	
}
