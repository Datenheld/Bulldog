package org.bulldog.devices.servo;

import org.bulldog.core.gpio.Pwm;

public class TowerProMicroSG90 extends Servo {

	private static final float MIN_ANGLE_DEFAULT = 0.02458f;
	private static final float MAX_ANGLE_DEFAULT = 0.12000f;
	private static final float INITIAL_ANGLE_DEFAULT = 0.0f;
	
	public TowerProMicroSG90(Pwm pwm) {
		super(pwm, INITIAL_ANGLE_DEFAULT, MIN_ANGLE_DEFAULT, MAX_ANGLE_DEFAULT);
	}
}
