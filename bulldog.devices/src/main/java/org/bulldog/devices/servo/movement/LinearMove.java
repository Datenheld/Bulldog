package org.bulldog.devices.servo.movement;

import org.bulldog.core.util.BulldogUtil;
import org.bulldog.devices.servo.Servo;

public class LinearMove implements Move {

	private float toAngle;
	private int milliseconds;
	
	public LinearMove(float toAngle, int milliseconds) {
		this.milliseconds = milliseconds;
		this.toAngle = toAngle;
	}
	
	@Override
	public void execute(Servo servo) {
		if(milliseconds <= 0) {
			new DirectMove(toAngle).execute(servo);
		} else {
			float startAngle = servo.getAngle();
			double delta = Math.abs(startAngle - toAngle);
			int sleepTime =  (int) (1000 / servo.getPwm().getFrequency());
			int amountSteps = (int)(milliseconds / sleepTime);
			float stepSize = (float)delta / amountSteps;
			for(int i = 0; i < amountSteps; i++) {
				if(startAngle < toAngle) {
					servo.setAngle(servo.getAngle() + stepSize);
				} else {
					servo.setAngle(servo.getAngle() - stepSize);
				}
				BulldogUtil.sleepMs(sleepTime);
			}
		}
	}

}
