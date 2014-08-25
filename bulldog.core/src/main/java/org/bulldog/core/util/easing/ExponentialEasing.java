package org.bulldog.core.util.easing;

public class ExponentialEasing implements Easing {
	
	public float  easeIn(float t, float d) {
		return (t==0) ? 0.0f : (float)Math.pow(2, 10 * (t/d - 1));
	}

	public float  easeOut(float t, float d) {
		return (t==d) ? 1.0f : (-(float)Math.pow(2, -10 * t/d) + 1);	
	}

	public float  easeInOut(float t, float d) {
		if (t==0) { return 0.0f; }
		if (t==d) { return 1.0f; }
		if ((t/=d/2) < 1) { return 0.5f * (float)Math.pow(2, 10 * (t - 1)); }
		
		return 0.5f * (-(float)Math.pow(2, -10 * (t-1)) + 2);
	}
	
}
