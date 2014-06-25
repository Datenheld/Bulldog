package org.bulldog.devices.actuators;

public interface Actuator {

	public void moveTo(double position);
	public void setPosition(double position);
	public double getPosition();
	
	public int getMillisecondsPerUnit();
	public int getRefreshIntervalMilliseconds();
}
