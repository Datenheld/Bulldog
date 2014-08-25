package org.bulldog.examples;

import org.bulldog.beagleboneblack.BBBNames;
import org.bulldog.beagleboneblack.BBBProperties;
import org.bulldog.core.gpio.Pwm;
import org.bulldog.core.gpio.util.SoftPwm;
import org.bulldog.core.platform.Board;
import org.bulldog.core.platform.Platform;
import org.bulldog.core.util.BulldogUtil;

public class PwmExample {

	public static void main(String... args) {
		
		final Board board = Platform.createBoard();
				
		//These will always share the same frequency - same pwm group!
		Pwm pwm1 = board.getPin(BBBNames.EHRPWM0A_P9_21).as(Pwm.class);
		Pwm pwm2 = board.getPin(BBBNames.EHRPWM0B_P9_22).as(Pwm.class);
		pwm1.setFrequency(50.0f);		// 50 Hz
		pwm1.setDuty(0.5f);				// 50% duty cycle
		pwm1.enable();
		
		pwm2.setFrequency(50.0f);		// 50 Hz
		pwm2.setDuty(0.3f);				// 30% duty cycle*/
		pwm2.enable();
		
		//These will always share the same frequency - same pwm group!
		Pwm pwm3 = board.getPin(BBBNames.EHRPWM1A_P9_14).as(Pwm.class);
		Pwm pwm4 = board.getPin(BBBNames.EHRPWM1B_P9_16).as(Pwm.class);
		pwm3.setFrequency(1000.0f);		// 1000 Hz
		pwm3.setDuty(0.5f);				// 50% duty cycle
		pwm3.enable();
		
		pwm4.setFrequency(1000.0f);		// 1000 Hz
		pwm4.setDuty(0.9f);				// 90% duty cycle
		pwm4.enable();
	
		//These will always share the same frequency - same pwm group!
		Pwm pwm5 = board.getPin(BBBNames.EHRPWM2A_P8_19).as(Pwm.class);
		Pwm pwm6 = board.getPin(BBBNames.EHRPWM2B_P8_13).as(Pwm.class);
		pwm5.setFrequency(10000.0f);	// 10 kHz
		pwm5.setDuty(0.25f);			// 25% duty cycle
		pwm5.enable();
		
		pwm6.setFrequency(10000.0f);
		pwm6.setDuty(0.75f);			// 75& duty cycle
		pwm6.enable();
		
		//On some pins there are conflicts with hdmi
		if(!board.hasProperty(BBBProperties.HDMI_ENABLED)) {
			Pwm pwm7 = board.getPin(BBBNames.ECAPPWM0_P9_42).as(Pwm.class);
			pwm7.setFrequency(100.0f);
			pwm7.setDuty(0.5f);
			pwm7.enable();
		
			Pwm pwm8 = board.getPin(BBBNames.ECAPPWM2_P9_28).as(Pwm.class);
			pwm8.setFrequency(100.0f);
			pwm8.setDuty(0.9f);
			pwm8.enable();
		}
		
		// This is a software pwm. It has kind of high cpu costs 
		// and is far from precise, but it can be used to drive 
		// motors for example and can be set up on any digital output
		Pwm softPwm = new SoftPwm(board.getPin(BBBNames.P8_12));
		softPwm.setFrequency(50.0f);
		softPwm.setDuty(0.2f);
		softPwm.enable();
		
		for(int i = 0; i < 10; i++) {
			for(double d = 0.0; d < 1.0; d += 0.01) {
				pwm5.setDuty(d);
				pwm6.setDuty(d);
			
				BulldogUtil.sleepMs(1);
			}
			
			for(double d = 1.0; d > 0.0; d -= 0.01) {
				pwm5.setDuty(d);
				pwm6.setDuty(d);
				
				BulldogUtil.sleepMs(1);
			}
		}
		
		System.out.println("You can exit with ctrl + c");
		while(true) {
			BulldogUtil.sleepMs(100);
		}
		
	}
}
