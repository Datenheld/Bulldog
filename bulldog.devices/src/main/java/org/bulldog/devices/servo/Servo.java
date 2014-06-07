package org.bulldog.devices.servo;

import org.bulldog.core.gpio.Pwm;

public class Servo {

	private static final float MIN_ANGLE_DEFAULT = 0.05f;
	private static final float MAX_ANGLE_DEFAULT = 0.10f;
	private static final float FREQUENCY_HZ = 50.0f;
	private static final float INITIAL_POSITION_DEFAULT = 0.0f;
	
	private Pwm pwm;
	private float angle;
	private float minAngleDuty;
	private float maxAngleDuty;

	public Servo (Pwm pwm) {
		this(pwm, INITIAL_POSITION_DEFAULT);
	}
	
	public Servo(Pwm pwm, float initialAngle) {
		this(pwm, initialAngle, MIN_ANGLE_DEFAULT, MAX_ANGLE_DEFAULT);
	}
	
	public Servo(Pwm pwm, float initialAngle, float minAngleDuty, float maxAngleDuty) {
		this.pwm = pwm;
		this.minAngleDuty = minAngleDuty;
		this.maxAngleDuty = maxAngleDuty;
		this.pwm.setFrequency(FREQUENCY_HZ);
		this.pwm.setDuty(getDutyForAngle(initialAngle));
		if(!this.pwm.isEnabled()) {
			this.pwm.enable();
		}
	}
	
	public void setAngle(float degrees) {
		angle = degrees;
		if(angle < 0.0f) {
			angle = 0.0f;
		}
		
		if(angle > 180.0f) {
			angle = 180.0f;
		}
		
		pwm.setDuty(getDutyForAngle(angle));
	}

	
	public void moveSmoothTo(float desiredAngle) {
		
	}
	
	public float getAngle() {
		return angle;
	}
	
	protected float getDutyForAngle(float angle) {
		float maxAngle = 180.0f;
		float anglePercent = angle/maxAngle;
		float dutyLength = (maxAngleDuty - minAngleDuty) * anglePercent;
		return minAngleDuty + dutyLength;
	}
	
	public Pwm getPwm() {
		return this.pwm;
	}
	
}
