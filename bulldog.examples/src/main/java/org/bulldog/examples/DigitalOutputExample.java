package org.bulldog.examples;

import java.io.IOException;

import org.bulldog.beagleboneblack.BBBNames;
import org.bulldog.core.Signal;
import org.bulldog.core.gpio.DigitalOutput;
import org.bulldog.core.platform.Board;
import org.bulldog.core.platform.Platform;
import org.bulldog.core.util.BulldogUtil;

public class DigitalOutputExample {

    public static void main(String[] args) throws IOException
    {
    	//Detect the board we are running on
    	Board board = Platform.createBoard();
    	
    	//Set up a digital input
    	DigitalOutput output = board.getPin(BBBNames.P8_12).as(DigitalOutput.class);
    	
    	//Set a high signal on the output
    	output.write(Signal.High);
    	
    	//Toggle it
    	output.toggle();
    	BulldogUtil.sleepMs(500);
    	
    	//Set it high
    	output.high();
    	BulldogUtil.sleepMs(500);
    	
    	//Set it low
    	output.low();
    	BulldogUtil.sleepMs(500);
    	
    	//Yet another way of applying a signal
    	output.applySignal(Signal.High);
    	
    	while(true) {
    		BulldogUtil.sleepMs(50);
    	}
  
    }
	
}
