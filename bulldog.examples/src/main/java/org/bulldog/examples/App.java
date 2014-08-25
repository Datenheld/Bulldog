package org.bulldog.examples;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.bulldog.beagleboneblack.BBBNames;
import org.bulldog.core.Signal;
import org.bulldog.core.gpio.DigitalInput;
import org.bulldog.core.platform.Board;
import org.bulldog.core.platform.Platform;
import org.bulldog.core.util.BulldogUtil;
import org.bulldog.devices.switches.Button;
import org.bulldog.devices.switches.ButtonListener;

/**
 * Hello world!
 *
 */
public class App 
{
	static class TestThread extends Thread {
		
		private boolean lowActive = false;
		public Exception ex;
		private Board board;
		private DigitalInput inputPin;
		private Button button;
		
		public TestThread() {
			 board = Platform.createBoard();
		     inputPin = board.getPin(BBBNames.P8_12).as(DigitalInput.class);
		     this.button = new Button(inputPin, lowActive ? Signal.Low : Signal.High);
		}
		
		public void run() {
			button.addListener(new ButtonListener() {
                 @Override
                 public void buttonReleased() {
                 	System.out.println("RELEASED");
                     //pressedListener.valueChanged(true, false);
                 }

                 @Override
                 public void buttonPressed() {
                 	System.out.println("PRESSED");
                     //pressedListener.valueChanged(false, true);
                 }
             });
        	 
        	 System.out.println(inputPin.getInterruptListeners().size());
             
            try {
            	
                while (true) {
                    sleep(10);
                }
                
            } catch (InterruptedException e) {
            	System.out.println("INTERRUPT");
            	//button.clearListeners();
            	 //inputPin.teardown();
                //logger.debug("interrupted...");
            } catch(Exception e) {
            	ex = e;
            	e.printStackTrace();
            } finally {
                button.clearListeners();
               
            }
		}
	}
	
    public static void main(String[] args) throws IOException
    {
    	 TestThread t = new TestThread();
    	 BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    	 String s = "";
    	 t.start();
    	 while((s = br.readLine()) != "exit\n") {
    		 t.interrupt();
    		 while(t.isAlive()) {
    			 BulldogUtil.sleepMs(100);
    		 }
    		 if(t.ex != null) {
    			 t.ex.printStackTrace();
    		 }
    		 t = new TestThread();
    		 t.start();
    	 }
    	
    }
}
