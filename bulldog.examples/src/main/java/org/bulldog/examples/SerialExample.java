package org.bulldog.examples;

import java.io.IOException;

import org.bulldog.beagleboneblack.BBBNames;
import org.bulldog.core.io.serial.SerialDataEventArgs;
import org.bulldog.core.io.serial.SerialDataListener;
import org.bulldog.core.io.serial.SerialPort;
import org.bulldog.core.platform.Board;
import org.bulldog.core.platform.Platform;
import org.bulldog.core.util.BulldogUtil;

/**
 * For this example, UART2 on the BeagleboneBlack ist connected to UART4.
 * UART2 reads the data from UART4 event driven and writes to UART4.
 * UART4 reads the data from UART2 synchronously and writes to UART2.
 * 
 * @author Datenheld
 *
 */
public class SerialExample {

	public static void main(String... args) throws IOException {
		
		//Get your platform
		final Board board = Platform.createBoard();
    	
		//Retrieve a serial port (UART2) and configure it
		SerialPort serial2 = board.getSerialPort(BBBNames.UART2);
    	serial2.setBaudRate(9600);
    	serial2.setBlocking(false);
    	serial2.open();
    	
    	//Add a listener... This will print the data once it is
    	//available for read on the port
    	serial2.addListener(new SerialDataListener() {

			@Override
			public void onSerialDataAvailable(SerialDataEventArgs args) {
				System.out.print(args.getDataAsString());
			}
    		
    	});;
    	
    	//Retrieve another serial port, UART4 in this case
    	SerialPort serial4 = board.getSerialPort(BBBNames.UART4);
    	serial4.setBaudRate(9600);
    	serial4.setBlocking(false);
    	serial4.open();
    	
    	for(int i = 0; i < 10; i++) {
    		serial2.writeString("Hello Serial4 - Greetings from Serial2\n");
    		
    		//Sleep, give UART2 time to transmit the data
    		BulldogUtil.sleepMs(50);
    		
    		System.out.print(serial4.readString());
    		serial4.writeString("Hello Serial2 - Greetings from Serial4\n");
    		BulldogUtil.sleepMs(1000);
    	}
    	
    	serial2.close();
    	serial4.close();
	}
	
}
