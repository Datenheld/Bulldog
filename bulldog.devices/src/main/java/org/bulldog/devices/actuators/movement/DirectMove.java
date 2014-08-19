package org.bulldog.devices.actuators.movement;

import org.bulldog.core.util.BulldogUtil;
import org.bulldog.devices.actuators.Actuator;

public class DirectMove implements Move {

	private double toPosition = 0.0f;
	
	public DirectMove(double toPosition) {
		this.toPosition = toPosition;
	}
	
	@Override
	public void execute(Actuator actuator) {
		double startPosition = actuator.getPosition();
		actuator.setPosition(toPosition);
		int sleepyTime = (int)Math.abs(toPosition - startPosition) * actuator.getMillisecondsPerUnit();
		BulldogUtil.sleepMs(sleepyTime);
	}
}
