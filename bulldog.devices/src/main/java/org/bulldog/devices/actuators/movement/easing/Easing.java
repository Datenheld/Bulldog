package org.bulldog.devices.actuators.movement.easing;

public interface Easing {

	float easeIn(float t, float d);
	float easeOut(float t, float d);
	float easeInOut(float t, float d);

}
