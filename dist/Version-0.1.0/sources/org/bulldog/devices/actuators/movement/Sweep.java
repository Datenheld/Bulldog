package org.bulldog.devices.actuators.movement;

import org.bulldog.devices.actuators.Actuator;


public class Sweep implements Move {

	private int durationMilliseconds;
	
	public Sweep(int durationMilliseconds) {
		this.durationMilliseconds = durationMilliseconds;
	}
	
	@Override
	public void execute(Actuator actuator) {
		new LinearMove(180.0, durationMilliseconds / 2).execute(actuator);
		new LinearMove(0.0, durationMilliseconds / 2).execute(actuator);
	}

}
