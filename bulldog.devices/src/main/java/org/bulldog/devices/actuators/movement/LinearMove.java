package org.bulldog.devices.actuators.movement;

import org.bulldog.core.util.BulldogUtil;
import org.bulldog.devices.actuators.Actuator;

public class LinearMove implements Move {

	private double toPosition;
	private int milliseconds;
	
	public LinearMove(double toPosition, int milliseconds) {
		this.milliseconds = milliseconds;
		this.toPosition = toPosition;
	}
	
	@Override
	public void execute(Actuator actuator) {
		if(milliseconds <= 0) {
			new DirectMove(toPosition).execute(actuator);
		} else {
			double startPosition = actuator.getPosition();
			double delta = Math.abs(startPosition - toPosition);
			int amountSteps = (int)(milliseconds / actuator.getRefreshIntervalMilliseconds());
			double stepSize = delta / amountSteps;
			for(int i = 0; i < amountSteps; i++) {
				if(startPosition < toPosition) {
					actuator.setPosition(actuator.getPosition() + stepSize);
				} else {
					actuator.setPosition(actuator.getPosition()- stepSize);
				}
				BulldogUtil.sleepMs(actuator.getRefreshIntervalMilliseconds());
			}
		}
	}

}
