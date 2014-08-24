package org.bulldog.examples;

import java.io.IOException;

import org.bulldog.beagleboneblack.BBBNames;
import org.bulldog.core.io.bus.i2c.I2cBus;
import org.bulldog.core.platform.Board;
import org.bulldog.core.platform.Platform;
import org.bulldog.core.util.BulldogUtil;
import org.bulldog.core.util.easing.BounceEasing;
import org.bulldog.core.util.easing.EasingOptions;
import org.bulldog.core.util.easing.ElasticEasing;
import org.bulldog.core.util.easing.QuintEasing;
import org.bulldog.core.util.easing.SineEasing;
import org.bulldog.devices.actuators.movement.EasedMove;
import org.bulldog.devices.servo.AdafruitServoDriver;
import org.bulldog.devices.servo.Servo;
import org.bulldog.devices.servo.ServoListener;
import org.bulldog.devices.servo.TowerProMicroSG90;

public class AdafruitServoDriverExample {
	
    public static void main(String[] args) throws IOException
    {
    	//Grab the platform the application is running on
    	Board board = Platform.createBoard();
    
    	I2cBus bus = board.getI2cBus(BBBNames.I2C_1);
    	AdafruitServoDriver servoDriver = new AdafruitServoDriver(bus, 0x70);
    	final Servo servo0 = new TowerProMicroSG90(servoDriver.getChannel(0));
    	final Servo servo1 = new TowerProMicroSG90(servoDriver.getChannel(1));
    	final Servo servo2 = new TowerProMicroSG90(servoDriver.getChannel(2));
    	final Servo servo3 = new TowerProMicroSG90(servoDriver.getChannel(3));
    	
    	ServoListener listener = new ServoListener() {

			@Override
			public void angleChanged(Servo servo, double oldAngle, double newAngle) {
				
			}

			@Override
			public void moveCompleted(Servo servo, double oldAngle, double newAngle) {
				double newDestination = newAngle == 90.0f ? 0.0f : 90.0f;
				
				if(servo == servo0) {
					servo.moveAsync(new EasedMove(new SineEasing(), newDestination, 1000));
				} else if(servo == servo1) {
					servo.moveAsync(new EasedMove(new QuintEasing(), newDestination, 1000));
				} else if(servo == servo2) {
					servo.moveAsync(new EasedMove(new BounceEasing(), newDestination, 1000, EasingOptions.EaseOut));
				} else if(servo == servo3) {
					servo.moveAsync(new EasedMove(new ElasticEasing(), newDestination, 1000, EasingOptions.EaseOut));
				}
			}
    		
    	};
    	
    	servo0.addServoListener(listener);
    	servo0.moveSmoothAsyncTo(90.0, 1000);
    	servo1.addServoListener(listener);
    	servo1.moveSmoothAsyncTo(90.0, 1000);
    	servo2.addServoListener(listener);
    	servo2.moveSmoothAsyncTo(90.0, 1000);
    	servo3.addServoListener(listener);
    	servo3.moveSmoothAsyncTo(90.0, 1000);
    	
    	while(true) {
    		BulldogUtil.sleepMs(1000);
    	}
  
    }
}
