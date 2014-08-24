package org.bulldog.examples;

import java.io.IOException;

import org.bulldog.beagleboneblack.BBBNames;
import org.bulldog.core.gpio.DigitalIO;
import org.bulldog.core.io.PinIOGroup;
import org.bulldog.core.platform.Board;
import org.bulldog.core.platform.Platform;
import org.bulldog.core.util.BulldogUtil;

public class PinIOGroupExample {

    public static void main(String[] args) throws IOException
    {
    	//Grab the platform the application is running on
    	Board board = Platform.createBoard();

    	PinIOGroup ioGroup = new PinIOGroup(board.getPin(BBBNames.P8_15).as(DigitalIO.class),
    										100,
    										board.getPin(BBBNames.P8_11).as(DigitalIO.class),
    										board.getPin(BBBNames.P8_12).as(DigitalIO.class),
    										board.getPin(BBBNames.P8_13).as(DigitalIO.class),
    										board.getPin(BBBNames.P8_14).as(DigitalIO.class)
    										
    			);
    	
    	while(true) {
    		for(int i = 0; i < 16; i++) {
    			ioGroup.writeByte(i);
    		}
    		BulldogUtil.sleepMs(100);
    	}
    }
	
}
