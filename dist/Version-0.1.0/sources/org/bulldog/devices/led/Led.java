package org.bulldog.devices.led;

import org.bulldog.core.gpio.Pwm;
import org.bulldog.core.gpio.util.PwmController;
import org.bulldog.core.util.easing.Easing;
import org.bulldog.core.util.easing.LinearEasing;

public class Led {

	private Pwm pwm;
	private PwmController pwmController;
	
	public Led(Pwm pwm) {
		this.pwm = pwm;
		this.pwmController = new PwmController();
	}
	
	public void fadeOut(int milliseconds) {
		pwmController.dutyTransition(pwm, 0.0, milliseconds, new LinearEasing());
	}
	
	public void fadeOut(int milliseconds, Easing easing) {
		pwmController.dutyTransition(pwm, 0.0, milliseconds, easing);
	}
	
	public void fadeIn(int milliseconds) {
		pwmController.dutyTransition(pwm, 1.0, milliseconds, new LinearEasing());
	}
	
	public void fadeIn(int milliseconds, Easing easing) {
		pwmController.dutyTransition(pwm, 1.0, milliseconds, easing);
	}
	
	public void blink() {
		
	}
	
	public void pulse() {
		
	}
	
}
