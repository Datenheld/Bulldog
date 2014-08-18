package org.bulldog.devices.servo;

import org.bulldog.core.gpio.Pwm;

public class TowerProMicroSG90 extends Servo {

	private static final double MIN_ANGLE_DEFAULT = 0.0225;
	private static final double MAX_ANGLE_DEFAULT = 0.1150;
	private static final double INITIAL_ANGLE_DEFAULT = 0.0;
	private static final int TIME_PER_DEGREE = (int)(0.1 / 60.0 * 1000);
	
	public TowerProMicroSG90(Pwm pwm) {
		super(pwm, INITIAL_ANGLE_DEFAULT, MIN_ANGLE_DEFAULT, MAX_ANGLE_DEFAULT, TIME_PER_DEGREE);
	}
}
