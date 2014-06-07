package org.bulldog.examples;

import java.io.IOException;

import org.bulldog.beagleboneblack.BBBNames;
import org.bulldog.core.io.bus.i2c.I2cBus;
import org.bulldog.core.platform.Board;
import org.bulldog.core.platform.Platform;
import org.bulldog.core.util.BulldogUtil;
import org.bulldog.devices.servo.AdafruitServoDriver;
import org.bulldog.devices.servo.TowerProMicroSG90;

public class AdafruitServoDriverExample {
	
    public static void main(String[] args) throws IOException
    {
    	//Grab the platform the application is running on
    	Board board = Platform.createBoard();
    
    	I2cBus bus = board.getI2cBus(BBBNames.I2C_1);
    	AdafruitServoDriver servoDriver = new AdafruitServoDriver(bus, 0x70);
    	TowerProMicroSG90 servo = new TowerProMicroSG90(servoDriver.getChannel(0));
    	TowerProMicroSG90 servo2 = new TowerProMicroSG90(servoDriver.getChannel(1));
    	
    	while(true) {
    		servo.setAngle(servo.getAngle() + 1.0f);
    		servo2.setAngle(servo2.getAngle() + 1.0f);
    		BulldogUtil.sleepMs(100);
    	}
  
    }
}
