package org.bulldog.core.util.easing;

public class SineEasing implements Easing {

	public float easeIn(float t, float d) {
		return -1.0f * (float) Math.cos(t / d * (Math.PI / 2)) + 1.0f;
	}

	public float easeOut(float t, float d) {
		return (float) Math.sin(t / d * (Math.PI / 2));
	}

	public float easeInOut(float t, float d) {
		return -0.5f * ((float) Math.cos(Math.PI * t / d) - 1);
	}
}
