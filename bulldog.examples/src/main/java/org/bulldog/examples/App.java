package org.bulldog.examples;

import java.io.IOException;

import org.bulldog.core.Signal;
import org.bulldog.core.gpio.DigitalInput;
import org.bulldog.core.gpio.DigitalOutput;
import org.bulldog.core.platform.Board;
import org.bulldog.core.platform.Platform;
import org.bulldog.core.util.BulldogUtil;
import org.bulldog.devices.switches.Button;
import org.bulldog.devices.switches.ButtonListener;
import org.bulldog.raspberrypi.RaspiNames;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main(String[] args) throws IOException
    {
    	Board board = Platform.createBoard();
    	DigitalOutput out = board.getPin(RaspiNames.P1_7).as(DigitalOutput.class);
    	DigitalInput in = board.getPin(RaspiNames.P1_5).as(DigitalInput.class);
    	
    	Button button = new Button(in, Signal.Low);
    	button.addListener(new ButtonListener() {

			@Override
			public void buttonPressed() {
				System.out.println("PRESSED");
				
			}

			@Override
			public void buttonReleased() {
				System.out.println("RELEASED");
			}
    		
    	});
    	//out.startBlinking(500);
    	
    	while(true) {
    		out.toggle();
    		BulldogUtil.sleepMs(1000);
    		System.out.println(in.read());
    	}
    }
}
