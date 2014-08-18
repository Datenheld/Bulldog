package org.bulldog.examples;

import java.io.IOException;

import org.bulldog.beagleboneblack.BBBNames;
import org.bulldog.core.Signal;
import org.bulldog.core.gpio.DigitalInput;
import org.bulldog.core.platform.Board;
import org.bulldog.core.platform.Platform;
import org.bulldog.core.util.BulldogUtil;
import org.bulldog.devices.switches.Button;
import org.bulldog.devices.switches.ButtonListener;

public class ButtonExample {
	
    public static void main(String[] args) throws IOException
    {
    	//Grab the platform the application is running on
    	Board board = Platform.createBoard();
    	
    	//Set up a digital input
    	DigitalInput buttonSignal = board.getPin(BBBNames.P8_12).as(DigitalInput.class);
    		
    	//Create the button with this DigitalInput
    	Button button = new Button(buttonSignal, Signal.Low);
    	
    	//Add a button listener
    	button.addListener(new ButtonListener() {

			public void buttonPressed() {
				System.out.println("PRESSED");
			}

			public void buttonReleased() {
				System.out.println("RELEASED");
			}
    		
    	});
    	
    	while(true) {
    		BulldogUtil.sleepMs(50);
    	}
  
    }
}
