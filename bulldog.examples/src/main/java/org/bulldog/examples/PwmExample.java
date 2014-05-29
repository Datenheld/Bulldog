package org.bulldog.examples;

import org.bulldog.beagleboneblack.BBBNames;
import org.bulldog.core.gpio.Pwm;
import org.bulldog.core.platform.Board;
import org.bulldog.core.platform.Platform;
import org.bulldog.core.util.BulldogUtil;

public class PwmExample {

	public static void main(String... args) {
		
		final Board board = Platform.createBoard();
		
		Pwm pwm1 = board.getPin(BBBNames.EHRPWM0A_P9_21).as(Pwm.class);
		Pwm pwm2 = board.getPin(BBBNames.EHRPWM0B_P9_22).as(Pwm.class);
		
		pwm1.setFrequency(50.0f);		// 50 Hz
		pwm1.setDuty(0.5f);				// 50% duty cycle
		
		pwm2.setFrequency(500.0f);		// 500 Hz
		pwm2.setDuty(0.3f);				// 30% duty cycle*/
		
		while(true) {
			BulldogUtil.sleepMs(1000);
		}
		
	}
}
