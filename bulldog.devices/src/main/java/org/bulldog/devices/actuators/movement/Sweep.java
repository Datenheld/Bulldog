package org.bulldog.devices.actuators.movement;

import org.bulldog.devices.actuators.Actuator;


public class Sweep implements Move {

	private int durationMilliseconds;
	
	public Sweep(int durationMilliseconds) {
		this.durationMilliseconds = durationMilliseconds;
	}
	
	@Override
	public void execute(Actuator actuator) {
		double currentPosition = actuator.getPosition();
		double totalDistance = (180.0 - currentPosition) + 180.0;
		
		double timeFactorFirstMove = (180.0 - currentPosition) / totalDistance;
		double timeFactorSecondMove = 1.0 - timeFactorFirstMove;
		
		new LinearMove(180.0, (int)Math.round(durationMilliseconds * timeFactorFirstMove)).execute(actuator);
		new LinearMove(0.0, (int)Math.round(durationMilliseconds * timeFactorSecondMove)).execute(actuator);
	}

}
