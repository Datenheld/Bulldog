package org.bulldog.devices.servo.movement;

import org.bulldog.core.util.BulldogUtil;
import org.bulldog.devices.servo.Servo;
import org.bulldog.devices.servo.movement.easing.Easing;
import org.bulldog.devices.servo.movement.easing.EasingOptions;

public class EasedMove implements Move {

	private Easing easing;
	private float desiredAngle;
	private int durationMilliseconds;
	private EasingOptions options = EasingOptions.EaseInOut;
	
	public EasedMove(Easing easing, float desiredAngle) {
		this(easing, desiredAngle, 0);
	}
	
	public EasedMove(Easing easing, float desiredAngle, int durationMilliseconds) {
		this.easing = easing;
		this.desiredAngle = desiredAngle;
		this.durationMilliseconds = durationMilliseconds;
	}
	
	public EasedMove(Easing easing, float desiredAngle, int durationMilliseconds, EasingOptions options) {
		this.easing = easing;
		this.desiredAngle = desiredAngle;
		this.durationMilliseconds = durationMilliseconds;
		this.options = options;
	}
	
	@Override
	public void execute(Servo servo) {
		float startAngle = servo.getAngle();
		double delta = Math.abs(startAngle - desiredAngle);
		if(durationMilliseconds <= 0) {
			durationMilliseconds = (int)delta * servo.getMillisecondsPerDegree();
		}
		
		int sleepTime =  (int) (1000 / servo.getPwm().getFrequency());
		int amountSteps = (int)(durationMilliseconds / sleepTime);
		boolean isInverse = servo.getAngle() > desiredAngle;
		for(int i = 0; i < amountSteps - 1; i++) {
			float positionFactor = options.calculate(easing, i, amountSteps);
			float step = (float) (positionFactor * delta);
			if(isInverse) {
				servo.setAngle((float) (startAngle - step));
			} else {
				servo.setAngle((float) (startAngle + step));
			}
			
			BulldogUtil.sleepMs(sleepTime);
		}
		
		servo.setAngle(desiredAngle);
		BulldogUtil.sleepMs(sleepTime);
	}

}
