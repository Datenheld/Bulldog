package org.bulldog.examples;

import java.io.IOException;

import org.bulldog.beagleboneblack.BBBNames;
import org.bulldog.core.gpio.DigitalIO;
import org.bulldog.core.gpio.DigitalOutput;
import org.bulldog.core.io.PinIOGroup;
import org.bulldog.core.platform.Board;
import org.bulldog.core.platform.Platform;
import org.bulldog.core.util.BulldogUtil;
import org.bulldog.devices.lcd.HD44780Compatible;
import org.bulldog.devices.lcd.HD44780Mode;
import org.bulldog.devices.lcd.Lcd;
import org.bulldog.devices.lcd.LcdFont;
import org.bulldog.devices.lcd.LcdMode;

/**
 * This example demonstrates how to use a HD44780 compatible (defacto standard)
 * LCD display in bulldog.
 * 
 * @author Dave
 *
 */
public class LcdExample {

    public static void main(String[] args) throws IOException
    {
    	Lcd display = null;
    	if(args.length > 0 && args[0].equals("4")) {
    		display = init4Bit();
    	} else {
    		display = init8Bit();
    	}
    	
    	doCoolStuffWithDisplay(display);
    }

    private static Lcd init4Bit() throws IOException {
    	//Grab the platform the application is running on
    	Board board = Platform.createBoard();

    	//Create a pin IO group. This is a nice way of grouping output pins to a parallel interface.
    	//We can use the IO group like a datastream then and just write nibbles to it.
    	PinIOGroup ioGroup = new PinIOGroup(board.getPin(BBBNames.P8_12).as(DigitalIO.class),  //enable pin
    										board.getPin(BBBNames.P8_17).as(DigitalIO.class),  //db 4
    										board.getPin(BBBNames.P9_22).as(DigitalIO.class),  //db 5
    										board.getPin(BBBNames.P9_23).as(DigitalIO.class),  //db 6
    										board.getPin(BBBNames.P8_26).as(DigitalIO.class)   //db 7
    										
    			);
    	
    	Lcd display = new HD44780Compatible(board.getPin(BBBNames.P8_11).as(DigitalOutput.class),    //rs pin
			    							board.getPin(BBBNames.P9_21).as(DigitalOutput.class),    //rw pin
			    							ioGroup,
			    							HD44780Mode.FourBit);
    	return display;
    }
    
	private static Lcd init8Bit() throws IOException {
		//Grab the platform the application is running on
    	Board board = Platform.createBoard();

    	//Create a pin IO group. This is a nice way of grouping output pins to a parallel interface.
    	//We can use the IO group like a datastream then and just write bytes to it.
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
    	
    	Lcd display = new HD44780Compatible(board.getPin(BBBNames.P8_11).as(DigitalOutput.class),    //rs pin
    							  			board.getPin(BBBNames.P9_21).as(DigitalOutput.class),    //rw pin
    							  			ioGroup,
    							  			HD44780Mode.EightBit);
    	
    	return display;
	}

	private static void doCoolStuffWithDisplay(Lcd display) {
		
		display.setMode(LcdMode.Display1x16, LcdFont.Font_8x10);
    	display.write("World Hello");
    	BulldogUtil.sleepMs(5000);
    	
    	display.setMode(LcdMode.Display1x16, LcdFont.Font_5x8);
    	display.write("Hello World");
    	BulldogUtil.sleepMs(5000);
    	
    	display.setMode(LcdMode.Display2x16, LcdFont.Font_5x8);
    	display.write("Hello World 2");
    	BulldogUtil.sleepMs(3000);
    	
    	display.blinkCursor(false);
    	BulldogUtil.sleepMs(3000);
    	
    	display.showCursor(false);
    	BulldogUtil.sleepMs(3000);
    	
    	display.showCursor(true);
    	display.blinkCursor(true);
    	display.setCursorPosition(1, 5);
    	display.write("test");
    	
    	System.out.println(display.read(1, 5, 4));
    	System.out.println(display.readLine(0));
    	System.out.println(display.readLine(1));
    	
    	display.home();
    	System.out.println(display.read(3));
    	
    	BulldogUtil.sleepMs(3000);
    	
    	display.clear();
	}
	
}
