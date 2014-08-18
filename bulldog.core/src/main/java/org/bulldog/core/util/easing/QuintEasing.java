package org.bulldog.core.util.easing;

public class QuintEasing implements Easing {

	public float easeIn (float t, float d) {
		return (t/=d)*t*t*t*t;
	}

	public float easeOut (float t, float d) {
		return ((t=t/d-1)*t*t*t*t + 1);
	}

	public float easeInOut (float t, float d) {
		if ((t/=d/2) < 1) { return 0.5f*t*t*t*t*t; }
		return 0.5f*((t-=2)*t*t*t*t + 2);
	}
}
