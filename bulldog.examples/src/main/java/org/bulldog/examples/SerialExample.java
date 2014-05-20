package org.bulldog.examples;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.bulldog.beagleboneblack.BBBNames;
import org.bulldog.core.io.SerialPort;
import org.bulldog.core.platform.Board;
import org.bulldog.core.platform.Platform;

public class SerialExample {

	public static void main(String... args) throws IOException {
		
		final Board board = Platform.createBoard();
    	
    	SerialPort serial = board.getSerialPort(BBBNames.UART2);
    	serial.setBaudRate(9600);
    	serial.setBlocking(true);
    	serial.open();
    	for(int i = 0; i < 10; i++) {
    		BufferedReader reader = new BufferedReader(new InputStreamReader(serial.getInputStream()));
    		System.out.println(reader.readLine());
    	}
    	
	}
	
}
