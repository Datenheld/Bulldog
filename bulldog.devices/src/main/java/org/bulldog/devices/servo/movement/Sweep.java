package org.bulldog.devices.servo.movement;

import org.bulldog.devices.servo.Servo;

public class Sweep implements Move {

	private int durationMilliseconds;
	
	public Sweep(int durationMilliseconds) {
		this.durationMilliseconds = durationMilliseconds;
	}
	
	@Override
	public void execute(Servo servo) {
		new LinearMove(180.0f, durationMilliseconds / 2).execute(servo);
		new LinearMove(0.0f, durationMilliseconds / 2).execute(servo);
	}

}
