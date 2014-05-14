package org.bulldog.examples;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.bulldog.beagleboneblack.BeagleBoneBlack;
import org.bulldog.core.Board;
import org.bulldog.core.gpio.AnalogInput;
import org.bulldog.core.util.BulldogUtil;

public class AdcExample {
	
    public static void main(String[] args) throws IOException, InterruptedException, ExecutionException
    {
    	//Instantiate the board
    	Board board = new BeagleBoneBlack();
    	
    	//Get some analogInputs
    	AnalogInput analogInput0 = board.getPinByName("P9_39").as(AnalogInput.class);
    	AnalogInput analogInput1 = board.getPinByName("P9_40").as(AnalogInput.class);
    	AnalogInput analogInput2 = board.getPinByName("P9_37").as(AnalogInput.class);
    	
    	//Sample those asynchronously
    	Future<double[]> futureChannel0 = analogInput0.sampleAsync(10);
    	Future<double[]> futureChannel1 = analogInput1.sampleAsync(1000, 50.0f);
    	
    	//Sample 2500 values on this thread with a frequency of 500Hz
    	//Should take ~ 5 seconds
    	System.out.println("SAMPLING STARTED: " + System.currentTimeMillis());
    	analogInput2.sample(2500, 500);
    	System.out.println("SAMPLING STARTED: " + System.currentTimeMillis());
    	
    	double[] channel0Values = futureChannel0.get();
    	double[] channel1Values = futureChannel1.get();
    	
    	while(true) {
    		
    		//Just read channel1 every 50ms synchronously
    		//We multiply by 1.8 because that is our voltage reference. You should
    		//multiply it by your original input voltage before you brought it
    		//down to the Beaglebone Pin levels
    		System.out.println("VALUE ON AIN0: " + analogInput0.readValue() * 1.8);
    		BulldogUtil.sleepMs(50);
    	}
  
    }
}
