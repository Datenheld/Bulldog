package org.bulldog.devices.servo.movement;

import org.bulldog.core.util.BulldogUtil;
import org.bulldog.devices.servo.Servo;

public class SmoothMove implements Move {

	private float toAngle;
	private int milliseconds;
	
	public SmoothMove(float toAngle) {
		this(toAngle, 0);
	}
	
	public SmoothMove(float toAngle, int milliseconds) {
		this.milliseconds = milliseconds;
		this.toAngle = toAngle;
	}
	
	public void execute(Servo servo) {
		moveSmooth(servo, toAngle, milliseconds);
	}
	
	protected void moveSmooth(Servo servo, float desiredAngle, int milliseconds) {
		float startAngle = servo.getAngle();
		double delta = Math.abs(startAngle - desiredAngle);
		if(milliseconds <= 0) {
			milliseconds = (int)delta * servo.getMillisecondsPerDegree();
		}
		
		int sleepTime =  (int) (1000 / servo.getPwm().getFrequency());
		int amountSteps = (int)(milliseconds / sleepTime);
			
		double stepSize = delta / amountSteps;
		double minAngle = Math.min(startAngle, desiredAngle);
		float[] discreteSteps = new float[amountSteps];
		
		boolean isInverse = servo.getAngle() > desiredAngle;
		for(int i = 0; i < amountSteps; i++) {
			discreteSteps[i] = calculateStep(i * stepSize, delta, minAngle, isInverse);	
			servo.setAngle(discreteSteps[i]);
			BulldogUtil.sleepMs(sleepTime);
		}
	}
	
	private static float calculateStep(double value, double delta, double offset, boolean invert) {
		
		float smoothValue = (float)Math.round(delta / Math.PI * ((Math.PI * value) / delta 
									- Math.cos((Math.PI * value) / delta) 
									* Math.sin((Math.PI * value) / delta))) ;
		if(invert) {
			return (float)(delta + offset - smoothValue);
		} 
		
		return (float)(smoothValue + offset);
	}
	
	
}
