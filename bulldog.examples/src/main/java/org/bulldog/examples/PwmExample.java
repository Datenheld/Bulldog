package org.bulldog.examples;

import org.bulldog.beagleboneblack.BBBNames;
import org.bulldog.core.gpio.Pwm;
import org.bulldog.core.platform.Board;
import org.bulldog.core.platform.Platform;
import org.bulldog.core.util.BulldogUtil;

public class PwmExample {

	public static void main(String... args) {
		
		final Board board = Platform.createBoard();
		
		Pwm pwm1 = board.getPin(BBBNames.EHRPWM0A).as(Pwm.class);
		Pwm pwm2 = board.getPin(BBBNames.EHRPWM1A).as(Pwm.class);
		Pwm pwm3 = board.getPin(BBBNames.EHRPWM2A).as(Pwm.class);
		
		pwm1.setFrequency(50.0f);		// 50 Hz
		pwm1.setDuty(0.5f);				// 50% duty cycle
		
		pwm2.setFrequency(500.0f);		// 50 Hz
		pwm2.setDuty(0.3f);				// 30% duty cycle*/
		
		pwm3.setFrequency(5000.0f);		// 500 Hz
		pwm3.setDuty(0.5f);				// 50% duty cycle
		
		
		while(true) {
			BulldogUtil.sleepMs(1000);
		}
		
	}
}
