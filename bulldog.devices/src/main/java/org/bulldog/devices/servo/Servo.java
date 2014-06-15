package org.bulldog.devices.servo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.bulldog.core.gpio.Pwm;
import org.bulldog.devices.servo.movement.DirectMove;
import org.bulldog.devices.servo.movement.EasedMove;
import org.bulldog.devices.servo.movement.LinearMove;
import org.bulldog.devices.servo.movement.Move;
import org.bulldog.devices.servo.movement.easing.SineEasing;

public class Servo {

	private static final float MIN_ANGLE_DEFAULT = 0.05f;
	private static final float MAX_ANGLE_DEFAULT = 0.10f;
	private static final float FREQUENCY_HZ = 50.0f;
	private static final float INITIAL_POSITION_DEFAULT = 0.0f;
	private static final int TIME_PER_DEGREE_DEFAULT = (int)(0.1f / 60.0f * 1000);
	
	private Pwm pwm;
	private float angle;
	private float minAngleDuty;
	private float maxAngleDuty;
	private int degreeMilliseconds; 
	
	private ExecutorService executor = Executors.newSingleThreadExecutor();
	private Future<?> currentMove = null;
	
	private List<ServoListener> listeners = Collections.synchronizedList(new ArrayList<ServoListener>());
	
	public Servo (Pwm pwm) {
		this(pwm, INITIAL_POSITION_DEFAULT);
	}
	
	public Servo(Pwm pwm, float initialAngle) {
		this(pwm, initialAngle, MIN_ANGLE_DEFAULT, MAX_ANGLE_DEFAULT, TIME_PER_DEGREE_DEFAULT);
	}
	
	public Servo(Pwm pwm, float initialAngle, float minAngleDuty, float maxAngleDuty, int degreeMilliseconds) {
		this.pwm = pwm;
		this.minAngleDuty = minAngleDuty;
		this.maxAngleDuty = maxAngleDuty;
		this.degreeMilliseconds = degreeMilliseconds;
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
	
	public void moveTo(float angle) {
		move(new DirectMove(angle));
	}
	
	public void moveTo(float angle, int milliseconds) {
		move(new LinearMove(angle, milliseconds));
	}
	
	public void moveSmoothTo(float angle) {
		move(new EasedMove(new SineEasing(), angle));
	}
	
	public void moveSmoothTo(float angle, int milliseconds) {
		move(new EasedMove(new SineEasing(), angle, milliseconds));
	}
	
	public void move(Move move) {
		float startAngle = getAngle();
		move.execute(this);
		fireMoveCompleted(startAngle, getAngle());
	}
	
	public void moveAsync(final Move move) {
		currentMove = executor.submit(new Runnable() {

			@Override
			public void run() {
				move(move);
			}
			
		});
	}

	public void moveAsyncTo(float angle) {
		moveAsync(new DirectMove(angle));
	}
	
	public void moveAsyncTo(float angle, int milliseconds) {
		moveAsync(new LinearMove(angle, milliseconds));
	}
	
	public void moveSmoothAsyncTo(float angle) {
		moveAsync(new EasedMove(new SineEasing(), angle));
	}
	
	public void moveSmoothAsyncTo(float angle, int milliseconds) {
		moveAsync(new EasedMove(new SineEasing(), angle, milliseconds));
	}
	
	public void awaitMoveCompleted() {
		if(currentMove != null) {
			try {
				currentMove.get();
			} catch (Exception e) {
				throw new RuntimeException(e);
			} 
		}
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
	
	public void addServoListener(ServoListener listener) {
		listeners.add(listener);
	}
	
	public void removeServoListener(ServoListener listener) {
		listeners.remove(listener);
	}
	
	public void clearServoListeners() {
		listeners.clear();
	}
	
	protected void fireAngleChanged(float oldAngle, float newAngle) {
		synchronized(listeners) {
			for(ServoListener listener : listeners) {
				listener.angleChanged(this, oldAngle, newAngle);
			}
		}
	}
	
	protected void fireMoveCompleted(float oldAngle, float newAngle) {
		synchronized(listeners) {
			for(ServoListener listener : listeners) {
				listener.moveCompleted(this, oldAngle, newAngle);
			}
		}
	}
	
	public int getMillisecondsPerDegree() {
		return degreeMilliseconds;
	}
}
