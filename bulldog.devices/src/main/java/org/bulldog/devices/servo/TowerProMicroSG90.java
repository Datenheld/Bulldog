package org.bulldog.devices.servo;

import org.bulldog.core.gpio.Pwm;

public class TowerProMicroSG90 extends Servo {

	private static final float MIN_ANGLE_DEFAULT = 0.0225f;
	private static final float MAX_ANGLE_DEFAULT = 0.1150f;
	private static final float INITIAL_ANGLE_DEFAULT = 0.0f;
	private static final int TIME_PER_DEGREE = (int)(0.1f / 60.0f * 1000);
	
	public TowerProMicroSG90(Pwm pwm) {
		super(pwm, INITIAL_ANGLE_DEFAULT, MIN_ANGLE_DEFAULT, MAX_ANGLE_DEFAULT, TIME_PER_DEGREE);
	}
}
