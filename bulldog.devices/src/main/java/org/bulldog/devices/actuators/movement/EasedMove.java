package org.bulldog.devices.actuators.movement;

import org.bulldog.core.util.BulldogUtil;
import org.bulldog.core.util.easing.Easing;
import org.bulldog.core.util.easing.EasingOptions;
import org.bulldog.devices.actuators.Actuator;

public class EasedMove implements Move {

	private Easing easing;
	private double toPosition;
	private int durationMilliseconds;
	private EasingOptions options = EasingOptions.EaseInOut;
	
	public EasedMove(Easing easing, double toPosition) {
		this(easing, toPosition, 0);
	}
	
	public EasedMove(Easing easing, double toPosition, int durationMilliseconds) {
		this.easing = easing;
		this.toPosition = toPosition;
		this.durationMilliseconds = durationMilliseconds;
	}
	
	public EasedMove(Easing easing, double toPosition, int durationMilliseconds, EasingOptions options) {
		this.easing = easing;
		this.toPosition = toPosition;
		this.durationMilliseconds = durationMilliseconds;
		this.options = options;
	}
	
	@Override
	public void execute(Actuator actuator) {
		double startPosition = actuator.getPosition();
		double delta = Math.abs(startPosition - toPosition);
		if(durationMilliseconds <= 0) {
			durationMilliseconds = (int) (delta * actuator.getMillisecondsPerUnit());
		}
		
		int amountSteps = (int)(durationMilliseconds / actuator.getRefreshIntervalMilliseconds());
		boolean isInverse = startPosition > toPosition;
		for(int i = 0; i < amountSteps - 1; i++) {
			double positionFactor = options.calculate(easing, i, amountSteps);
			double step = positionFactor * delta;
			if(isInverse) {
				actuator.setPosition(startPosition - step);
			} else {
				actuator.setPosition(startPosition + step);
			}
			
			BulldogUtil.sleepMs(actuator.getRefreshIntervalMilliseconds());
		}
		
		actuator.setPosition(toPosition);
		BulldogUtil.sleepMs(actuator.getRefreshIntervalMilliseconds());
	}

}
