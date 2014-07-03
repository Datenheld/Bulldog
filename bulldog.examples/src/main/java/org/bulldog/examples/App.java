package org.bulldog.examples;

import java.io.IOException;

import org.bulldog.core.gpio.DigitalOutput;
import org.bulldog.core.platform.Board;
import org.bulldog.core.platform.Platform;
import org.bulldog.core.util.BulldogUtil;
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
    	//out.startBlinking(500);
    	
    	while(true) {
    		out.toggle();
    		BulldogUtil.sleepMs(1000);
    	}
    }
}
