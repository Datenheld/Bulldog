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
 * For this example, UART2 on the BeagleboneBlack is connected to UART1.
 * UART2 reads the data from UART1 event driven and writes to UART1.
 * UART1 reads the data from UART2 synchronously and writes to UART2.
 * 
 * @author Datenheld
 *
 */
public class App {

	public static void main(String... args) throws IOException {
		
		//Get your platform
		final Board board = Platform.createBoard();
    	
		//Retrieve a serial port (UART2) and configure it
		SerialPort serial2 = board.getSerialPort(BBBNames.UART2);
    	serial2.setBaudRate(100000);
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
    	
    	while(true) {
    		BulldogUtil.sleepMs(1000);
    	}
	}
	
}
