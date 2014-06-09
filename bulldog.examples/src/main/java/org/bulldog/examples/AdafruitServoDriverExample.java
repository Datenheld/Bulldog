package org.bulldog.examples;

import java.io.IOException;

import org.bulldog.beagleboneblack.BBBNames;
import org.bulldog.core.io.bus.i2c.I2cBus;
import org.bulldog.core.platform.Board;
import org.bulldog.core.platform.Platform;
import org.bulldog.core.util.BulldogUtil;
import org.bulldog.devices.servo.AdafruitServoDriver;
import org.bulldog.devices.servo.Servo;
import org.bulldog.devices.servo.ServoListener;
import org.bulldog.devices.servo.TowerProMicroSG90;
import org.bulldog.devices.servo.movement.Sweep;

public class AdafruitServoDriverExample {
	
    public static void main(String[] args) throws IOException
    {
    	//Grab the platform the application is running on
    	Board board = Platform.createBoard();
    
    	I2cBus bus = board.getI2cBus(BBBNames.I2C_1);
    	AdafruitServoDriver servoDriver = new AdafruitServoDriver(bus, 0x70);
    	final TowerProMicroSG90 servo0 = new TowerProMicroSG90(servoDriver.getChannel(0));
    	final TowerProMicroSG90 servo1 = new TowerProMicroSG90(servoDriver.getChannel(1));
    	final TowerProMicroSG90 servo2 = new TowerProMicroSG90(servoDriver.getChannel(2));
    	
    	ServoListener listener = new ServoListener() {

			@Override
			public void angleChanged(Servo servo, float oldAngle, float newAngle) {
				
			}

			@Override
			public void moveCompleted(Servo servo, float oldAngle, float newAngle) {
				if(newAngle == 90.0f) {
					servo.moveSmoothAsyncTo(0.0f, 5000);
				} else {
					servo.moveSmoothAsyncTo(90.0f, 5000);
				}
				
				if(servo == servo1) {
		    		servo2.moveAsync(new Sweep(2000));
				}
			}
    		
    	};
    	
    	servo0.addServoListener(listener);
    	servo0.moveSmoothAsyncTo(90.0f, 5000);
    	servo1.addServoListener(listener);
    	servo1.moveSmoothAsyncTo(90.0f, 5000);
    	
    	
    	while(true) {
    		BulldogUtil.sleepMs(1000);
    	}
  
    }
}
