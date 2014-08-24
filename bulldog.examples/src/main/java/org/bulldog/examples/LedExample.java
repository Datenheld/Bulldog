package org.bulldog.examples;

import java.io.IOException;

import org.bulldog.beagleboneblack.BBBNames;
import org.bulldog.core.gpio.Pwm;
import org.bulldog.core.platform.Board;
import org.bulldog.core.platform.Platform;
import org.bulldog.core.util.BulldogUtil;
import org.bulldog.devices.led.Led;

public class LedExample {

	public static void main(String... args) throws IOException {
		
		//Get your platform
		final Board board = Platform.createBoard();
    	
		//Get a PWM
		Pwm pwm = board.getPin(BBBNames.PWM_P8_13).as(Pwm.class);
	
		//Construct the LED with it
		Led led = new Led(pwm);
		
		led.setBrightness(1.0);
		BulldogUtil.sleepMs(1000);
		
		led.setBrightness(0.5);
		BulldogUtil.sleepMs(1000);
		
		for(int i = 0; i < 5; i++) {
			led.fadeIn(1000);
			led.fadeOut(1000);
		}
	}
}
