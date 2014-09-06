package org.bulldog.examples;

import java.io.IOException;

import org.bulldog.linux.jni.NativeTools;
import org.bulldog.linux.util.LinuxLibraryLoader;

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
		LinuxLibraryLoader.loadNativeLibrary();
		
    	while(true) {
    		NativeTools.sleepMicros(1000);
    		System.out.println(System.nanoTime());
    	}
	}
	
}
