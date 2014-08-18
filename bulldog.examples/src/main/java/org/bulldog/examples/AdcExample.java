package org.bulldog.examples;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.bulldog.beagleboneblack.BBBNames;
import org.bulldog.core.gpio.AnalogInput;
import org.bulldog.core.platform.Board;
import org.bulldog.core.platform.Platform;
import org.bulldog.core.util.BulldogUtil;

public class AdcExample {
	
    public static void main(String[] args) throws IOException, InterruptedException, ExecutionException
    {
    	//Detect the board
    	Board board = Platform.createBoard();
    	
    	//Get some analogInputs
    	AnalogInput analogInput0 = board.getPin(BBBNames.AIN0).as(AnalogInput.class);
    	AnalogInput analogInput1 = board.getPin(BBBNames.AIN1).as(AnalogInput.class);
    	AnalogInput analogInput2 = board.getPin(BBBNames.AIN2).as(AnalogInput.class);
    	
    	//Sample those asynchronously
    	Future<double[]> futureChannel0 = analogInput0.sampleAsync(10);
    	Future<double[]> futureChannel1 = analogInput1.sampleAsync(1000, 50.0f);
    	
    	//Sample 2500 values on this thread with a frequency of 500Hz
    	//Should take ~ 5 seconds
    	System.out.println("SAMPLING STARTED: " + System.currentTimeMillis());
    	analogInput2.sample(2500, 500);
    	System.out.println("SAMPLING STARTED: " + System.currentTimeMillis());
    	
    	double[] channel0Values = futureChannel0.get();
    	for(int i = 0; i< channel0Values.length; i++) {
    		System.out.println("Channel 0 measured: " + channel0Values[i]);
    	}
    	
    	double[] channel1Values = futureChannel1.get();
     	for(int i = 0; i< channel1Values.length; i++) {
    		System.out.println("Channel 1 measured: " + channel1Values[i]);
    	}
    	
    	while(true) {
    		
    		//Just read channel1 every 50ms synchronously
    		//We multiply by 1.8 because that is our voltage reference. You should
    		//multiply it by your original input voltage before you brought it
    		//down to the Beaglebone Pin levels
    		System.out.println("VALUE ON AIN0: " + analogInput0.read() * 1.8);
    		BulldogUtil.sleepMs(50);
    	}
  
    }
}
