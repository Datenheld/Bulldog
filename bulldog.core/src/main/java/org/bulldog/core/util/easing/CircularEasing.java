package org.bulldog.core.util.easing;

public class CircularEasing implements Easing {
	
	public float easeIn(float t, float d) {
		return -1.0f * ((float) Math.sqrt(1 - (t /= d) * t) - 1);
	}

	public float easeOut(float t, float d) {
		return (float) Math.sqrt(1 - (t = t / d - 1) * t);
	}

	public float easeInOut(float t, float d) {
		if ((t /= d / 2) < 1) {
			return -0.5f * ((float) Math.sqrt(1.0f - t * t) - 1);
		}
		
		return 0.5f * ((float) Math.sqrt(1 - (t -= 2) * t) + 1);
	}

}
