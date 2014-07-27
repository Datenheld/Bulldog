package org.bulldog.examples;

import java.io.IOException;

import org.bulldog.core.gpio.DigitalOutput;
import org.bulldog.core.io.PinIOGroup;
import org.bulldog.core.platform.Board;
import org.bulldog.core.platform.Platform;
import org.bulldog.devices.lcd.HD44780;
import org.bulldog.raspberrypi.RaspiNames;

public class LcdExample {

    public static void main(String[] args) throws IOException
    {
    	//Grab the platform the application is running on
    	Board board = Platform.createBoard();

    	/*PinIOGroup ioGroup = new PinIOGroup(board.getPin(RaspiNames.P1_3),
    										board.getPin(RaspiNames.P1_5),
    										board.getPin(RaspiNames.P1_7),
    										board.getPin(RaspiNames.P1_11),
    										board.getPin(RaspiNames.P1_13),
    										board.getPin(RaspiNames.P1_15),
    										board.getPin(RaspiNames.P1_19),
    										board.getPin(RaspiNames.P1_21),
    										board.getPin(RaspiNames.P1_23)
    										
    			);
    	
    	HD44780 display = new HD44780(board.getPin(RaspiNames.P1_10).as(DigitalOutput.class),
    								  board.getPin(RaspiNames.P1_12).as(DigitalOutput.class),
    								  ioGroup);
    	
    	display.init();
    	display.writeString("Hello World");*/
  
    }
	
}
