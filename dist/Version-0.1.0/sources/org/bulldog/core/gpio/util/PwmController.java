package org.bulldog.core.gpio.util;

import org.bulldog.core.gpio.Pwm;
import org.bulldog.core.util.easing.Easing;

public class PwmController {

	public void dutyTransition(Pwm pwm, double toDuty, int milliseconds, Easing easing) {
		long startTime = System.currentTimeMillis();
		double startDuty = pwm.getDuty();
		double diff = Math.abs(startDuty - toDuty);
		
		long currentTime = 0;
		while(currentTime < milliseconds) {
			currentTime = System.currentTimeMillis() - startTime;
			double currentDuty = diff * easing.easeInOut(currentTime, milliseconds);
			if(startDuty < toDuty) {
				pwm.setDuty(startDuty + currentDuty);
			} else {
				pwm.setDuty(startDuty - currentDuty);
			}
		}
	}
	
	public void frequencyTransition(Pwm pwm, double toFrequency, int milliseconds, Easing easing) {
		long startTime = System.currentTimeMillis();
		double startFrequency = pwm.getFrequency();
		double diff = Math.abs(startFrequency - toFrequency);
		
		long currentTime = 0;
		while(currentTime < milliseconds) {
			currentTime = System.currentTimeMillis() - startTime;
			double currentDuty = diff * easing.easeInOut(currentTime, milliseconds);
			if(startFrequency < toFrequency) {
				pwm.setDuty(startFrequency + currentDuty);
			} else {
				pwm.setDuty(startFrequency - currentDuty);
			}
		}
	}
	
}
