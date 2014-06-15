package org.bulldog.devices.servo.movement.easing;

public class QuintEasing implements Easing {

	public float easeIn (float t, float d) {
		return 1.0f*(t/=d)*t*t*t*t;
	}

	public float easeOut (float t, float d) {
		return 1.0f*((t=t/d-1)*t*t*t*t + 1);
	}

	public float easeInOut (float t, float d) {
		if ((t/=d/2) < 1) return 1.0f/2*t*t*t*t*t;
		return 1.0f/2.0f*((t-=2)*t*t*t*t + 2);
	}
}
