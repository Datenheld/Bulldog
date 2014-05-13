package org.bulldog.examples;

import java.io.IOException;

import org.bulldog.beagleboneblack.BeagleBoneBlack;
import org.bulldog.core.Board;
import org.bulldog.core.Signal;
import org.bulldog.core.gpio.DigitalOutput;
import org.bulldog.core.util.BulldogUtil;

public class DigitalOutputExample {

    public static void main(String[] args) throws IOException
    {
    	//Instantiate the board
    	Board board = new BeagleBoneBlack();
    	
    	//Set up a digital input
    	DigitalOutput output = board.getPinByName("P8_12").as(DigitalOutput.class);
    	
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
