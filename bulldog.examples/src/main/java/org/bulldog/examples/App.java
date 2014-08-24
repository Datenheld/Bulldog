package org.bulldog.examples;

import java.io.IOException;

import org.bulldog.beagleboneblack.BBBNames;
import org.bulldog.core.platform.Board;
import org.bulldog.core.platform.Platform;
import org.bulldog.devices.lcd.I2CLcd;
import org.bulldog.devices.lcd.Lcd;

/**
 * Hello world!
 *
 */
public class App 
{

	
    public static void main(String[] args) throws IOException
    {
    	Board board = Platform.createBoard();
    	Lcd lcd = new I2CLcd(board.getI2cBus(BBBNames.I2C_1), 0x20);
    	lcd.write("Hello World");
    	
    }
}
