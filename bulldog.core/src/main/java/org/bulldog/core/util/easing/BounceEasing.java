package org.bulldog.core.util.easing;

public class BounceEasing implements Easing {

	public float easeIn(float t, float d) {
		return 1.0f - easeOut(d - t, d);
	}

	public float easeOut(float t, float d) {
		if ((t /= d) < (1 / 2.75f)) {
			return 1.0f * (7.5625f * t * t);
		} else if (t < (2 / 2.75f)) {
			return 1.0f * (7.5625f * (t -= (1.5f / 2.75f)) * t + .75f);
		} else if (t < (2.5 / 2.75)) {
			return 1.0f * (7.5625f * (t -= (2.25f / 2.75f)) * t + .9375f);
		} else {
			return 1.0f * (7.5625f * (t -= (2.625f / 2.75f)) * t + .984375f);
		}
	}

	public float easeInOut(float t, float d) {
		if (t < d / 2) {
			return easeIn(t * 2, d) * 0.5f;
		} else {
			return easeOut(t * 2 - d, d) * .5f + 0.5f;
		}
	}

}
