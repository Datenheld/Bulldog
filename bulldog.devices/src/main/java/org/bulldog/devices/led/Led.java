package org.bulldog.devices.led;

import org.bulldog.core.gpio.Pwm;
import org.bulldog.core.gpio.util.PwmController;
import org.bulldog.core.util.easing.Easing;
import org.bulldog.core.util.easing.EasingOptions;
import org.bulldog.core.util.easing.SineEasing;

public class Led {

	private Pwm pwm;
	private PwmController pwmController;
	private Easing defaultEasing;
	
	public Led(Pwm pwm) {
		this(pwm, new SineEasing());
	}
	
	public Led(Pwm pwm, Easing defaultEasing) {
		this.pwm = pwm;
		this.defaultEasing = defaultEasing;
		this.pwmController = new PwmController();
		pwm.setFrequency(10000.0);
		pwm.setDuty(0.0);
		pwm.enable();
	}
	
	public void fadeOut(int milliseconds) {
		fadeOut(milliseconds, defaultEasing);
	}
	
	public void fadeOut(int milliseconds, Easing easing) {
		pwmController.dutyTransition(pwm, 0.0, milliseconds, easing, EasingOptions.EaseOut);
	}
	
	public void fadeIn(int milliseconds) {
		fadeIn(milliseconds, defaultEasing);
	}
	
	public void fadeIn(int milliseconds, Easing easing) {
		pwmController.dutyTransition(pwm, 1.0, milliseconds, easing, EasingOptions.EaseIn);
	}
	
	public void fadeToBrightness(int milliseconds, double toBrightness, Easing easing) {
		pwmController.dutyTransition(pwm, toBrightness, milliseconds, easing, EasingOptions.EaseIn);
	}
	
	public void fadeToBrightness(int milliseconds, double toBrightness) {
		fadeToBrightness(milliseconds, toBrightness, defaultEasing);
	}
	
	public void setBrightness(double brightness) {
		pwm.setDuty(brightness);
	}
		
	public void on() {
		pwm.enable();
	}
	
	public void off() {
		pwm.disable();
	}
	
}
