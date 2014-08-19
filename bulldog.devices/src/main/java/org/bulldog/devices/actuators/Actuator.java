package org.bulldog.devices.actuators;

import org.bulldog.devices.actuators.movement.Move;

public interface Actuator {

	public void moveTo(double position);
	public void setPosition(double position);
	public double getPosition();
	
	public void move(Move move);
	public void moveAsync(Move move);
	public void awaitMoveCompleted();
	public boolean isMoving();
	
	public int getMillisecondsPerUnit();
	public int getRefreshIntervalMilliseconds();
}
