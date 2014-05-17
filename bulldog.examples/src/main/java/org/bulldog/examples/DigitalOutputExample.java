package org.bulldog.examples;

import java.io.IOException;

import org.bulldog.core.Signal;
import org.bulldog.core.gpio.DigitalOutput;
import org.bulldog.core.platform.Board;
import org.bulldog.core.platform.Platform;
import org.bulldog.core.util.BulldogUtil;

public class DigitalOutputExample {

    public static void main(String[] args) throws IOException
    {
    	//Detect the board we are running on
    	Board board = Platform.detectBoard();
    	
    	//Set up a digital input
    	DigitalOutput output = board.getPin("P8", 12).as(DigitalOutput.class);
    	
    	//set a high signal on the output
    	output.applySignal(Signal.High);
    	
    	output.toggle();
    	BulldogUtil.sleepMs(1000);
    	
    	output.toggle();
    	BulldogUtil.sleepMs(1000);
    	
    	while(true) {
    		BulldogUtil.sleepMs(50);
    	}
  
    }
	
}
