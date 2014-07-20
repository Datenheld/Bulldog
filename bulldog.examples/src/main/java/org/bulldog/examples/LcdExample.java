package org.bulldog.examples;

import java.io.IOException;

import org.bulldog.beagleboneblack.BBBNames;
import org.bulldog.core.gpio.DigitalIO;
import org.bulldog.core.gpio.DigitalOutput;
import org.bulldog.core.io.PinIOGroup;
import org.bulldog.core.platform.Board;
import org.bulldog.core.platform.Platform;
import org.bulldog.core.util.BulldogUtil;
import org.bulldog.devices.lcd.HD44780;
import org.bulldog.devices.lcd.HD44780Mode;
import org.bulldog.devices.lcd.LcdFont;

public class LcdExample {

    public static void main(String[] args) throws IOException
    {
    	if(args.length > 0 && args[0].equals("4")) {
    		System.out.println("Running 4 bit example");
    		example4Bit();
    	} else {
    		System.out.println("Running 8 bit example");
    		example8Bit();
    	}
    }

    private static void example4Bit() throws IOException {
    	//Grab the platform the application is running on
    	Board board = Platform.createBoard();

    	PinIOGroup ioGroup = new PinIOGroup(board.getPin(BBBNames.P8_12).as(DigitalIO.class),  //enable pin
    										board.getPin(BBBNames.P8_17).as(DigitalIO.class),  //db 4
    										board.getPin(BBBNames.P9_22).as(DigitalIO.class),  //db 5
    										board.getPin(BBBNames.P9_23).as(DigitalIO.class),  //db 6
    										board.getPin(BBBNames.P8_26).as(DigitalIO.class)   //db 7
    										
    			);
    	
    	HD44780 display = new HD44780(board.getPin(BBBNames.P8_11).as(DigitalOutput.class),    //rs pin
    								  board.getPin(BBBNames.P9_15).as(DigitalOutput.class),    //rw pin
    								  ioGroup,
    								  HD44780Mode.FourBit);
    	
    	display.init(1, LcdFont.Font_8x10);
    	display.write("World Hello");
    	BulldogUtil.sleepMs(5000);
    	
    	display.init(1, LcdFont.Font_5x8);
    	display.write("Hello World");
    	BulldogUtil.sleepMs(5000);
    	
    	display.init(2, LcdFont.Font_5x8);
    	display.write("Hello World 2");
    	BulldogUtil.sleepMs(3000);
    	
    	display.blinkCursor(false);
    	BulldogUtil.sleepMs(3000);
    	
    	display.showCursor(false);
    	BulldogUtil.sleepMs(3000);
    	
    	display.clear();
    }
    
	private static void example8Bit() throws IOException {
		//Grab the platform the application is running on
    	Board board = Platform.createBoard();

    	PinIOGroup ioGroup = new PinIOGroup(board.getPin(BBBNames.P8_12).as(DigitalIO.class),  //enable pin
    										board.getPin(BBBNames.P8_13).as(DigitalIO.class),  //db 0
    										board.getPin(BBBNames.P8_14).as(DigitalIO.class),  //db 1
    										board.getPin(BBBNames.P8_15).as(DigitalIO.class),  //db 2
    										board.getPin(BBBNames.P8_16).as(DigitalIO.class),  //db 3
    										board.getPin(BBBNames.P8_17).as(DigitalIO.class),  //db 4
    										board.getPin(BBBNames.P9_22).as(DigitalIO.class),  //db 5
    										board.getPin(BBBNames.P9_23).as(DigitalIO.class),  //db 6
    										board.getPin(BBBNames.P8_26).as(DigitalIO.class)   //db 7
    										
    			);
    	
    	HD44780 display = new HD44780(board.getPin(BBBNames.P8_11).as(DigitalOutput.class),    //rs pin
    								  board.getPin(BBBNames.P9_15).as(DigitalOutput.class),    //rw pin
    								  ioGroup,
    								  HD44780Mode.EightBit);
    	
    	display.init(1, LcdFont.Font_8x10);
    	display.write("World Hello");
    	BulldogUtil.sleepMs(5000);
    	
    	display.init(1, LcdFont.Font_5x8);
    	display.write("Hello World");
    	BulldogUtil.sleepMs(5000);
    	
    	display.init(2, LcdFont.Font_5x8);
    	display.write("Hello World 2");
    	BulldogUtil.sleepMs(3000);
    	
    	display.blinkCursor(false);
    	BulldogUtil.sleepMs(3000);
    	
    	display.showCursor(false);
    	BulldogUtil.sleepMs(3000);
    	
    	display.clear();
	}
	
}
