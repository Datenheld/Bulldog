package org.bulldog.devices.servo.movement;

import org.bulldog.core.util.BulldogUtil;
import org.bulldog.devices.servo.Servo;

public class DirectMove implements Move {

	private float toAngle = 0.0f;
	
	public DirectMove(float toAngle) {
		this.toAngle = toAngle;
	}
	

	@Override
	public void execute(Servo servo) {
		float startAngle = servo.getAngle();
		servo.setAngle(toAngle);
		int sleepyTime = (int)Math.abs(toAngle - startAngle) * servo.getMillisecondsPerDegree();
		BulldogUtil.sleepMs(sleepyTime);
	}
	
	
	
}
