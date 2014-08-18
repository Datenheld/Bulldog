package org.bulldog.core.util.easing;

public enum EasingOptions {
	EaseIn,
	EaseOut,
	EaseInOut;
	
	public float calculate(Easing easing, float t, float d) {
		if(this == EasingOptions.EaseIn) {
			return easing.easeIn(t, d); 
		} else if(this == EasingOptions.EaseOut) {
			return easing.easeOut(t, d);
		} 
		
		return easing.easeInOut(t, d);
	}
}
