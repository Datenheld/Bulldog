package org.bulldog.core.util.easing;

public class LinearEasing implements Easing {
	
	public float easeIn(float t, float d) {
		return t/d;
	}

	public float easeOut(float t, float d) {
		return t/d;
	}

	public float easeInOut(float t, float d) {
		return t/d;
	}

}
