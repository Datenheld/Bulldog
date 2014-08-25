package org.bulldog.examples;

import java.io.IOException;

import org.bulldog.beagleboneblack.BBBNames;
import org.bulldog.core.gpio.Pwm;
import org.bulldog.core.platform.Board;
import org.bulldog.core.platform.Platform;
import org.bulldog.core.util.BulldogUtil;
import org.bulldog.core.util.easing.EasingOptions;
import org.bulldog.core.util.easing.SineEasing;
import org.bulldog.devices.actuators.movement.EasedMove;
import org.bulldog.devices.actuators.movement.LinearMove;
import org.bulldog.devices.actuators.movement.Sweep;
import org.bulldog.devices.servo.Servo;
import org.bulldog.devices.servo.ServoListener;
import org.bulldog.devices.servo.TowerProMicroSG90;

public class ServoExample {
	
    public static void main(String[] args) throws IOException
    {
    	// Grab the platform the application is running on
    	Board board = Platform.createBoard();
    	
    	Pwm pwm = board.getPin(BBBNames.PWM_P8_13).as(Pwm.class);
    	Servo servo = new TowerProMicroSG90(pwm);
    	
    	// Controlling the servo by setting the angle
    	// Alternatively, the setPosition() method can be used to set
    	// the angle.
    	servo.setAngle(90.0f);
    	BulldogUtil.sleepMs(1000);
    	
    	servo.setAngle(180.0f);
    	BulldogUtil.sleepMs(1000);
    	
    	servo.setAngle(0.0f);
    	BulldogUtil.sleepMs(1000);
    	
    	// Controlling the servo using the move API
    	System.out.println("Moving to 45 degrees");
    	servo.moveTo(45.0f);
    	
    	// move 45 degrees (from 45 degrees to 90 degrees) in 3000 milliseconds
    	System.out.println("Moving to 90 degrees");
    	servo.moveTo(90.0f, 2000);
    	
    	// performing a sweep (90 degrees to 180 degrees to 0 degrees in 5000 milliseconds)
    	System.out.println("Sweeping");
    	servo.move(new Sweep(3000));
    	
    	// perform a linear move - move to position in specified time
    	System.out.println("Moving to 60 in 2 seconds degrees");
    	servo.move(new LinearMove(60.0f, 2000));
    	
    	// perform an eased move (a smoothed move) to 120 degrees in 2000 milliseconds
    	System.out.println("Moving to 120 degrees in 2 seconds");
    	servo.move(new EasedMove(new SineEasing(), 120.0f, 2000, EasingOptions.EaseInOut));
    	
    	// perform a smooth move
    	System.out.println("Moving to 0 degrees as fast as possible, but smoothed");
    	servo.moveSmoothTo(0.0f);
    	
    	// move async
    	servo.moveAsyncTo(120.0f, 3000);
    	System.out.println("SERVO now moving asynchronously");
    	servo.awaitMoveCompleted();
    	
    	servo.moveSmoothAsyncTo(180.0f, 3000);
    	while(servo.isMoving()) {
    		System.out.println("SERVO MOVING");
    		BulldogUtil.sleepMs(1000);
    	}

    	// You can add listener to the servos
    	servo.addServoListener(new ServoListener() {

			@Override
			public void angleChanged(Servo servo, double oldAngle,
					double newAngle) {
				System.out.println("Angle changed - [from: " + oldAngle + "] [to: " + newAngle + "]");
			}

			@Override
			public void moveCompleted(Servo servo, double oldAngle,
					double newAngle) {
				System.out.println("Servo completed move");
			}
    		
    	});
    	
    	servo.moveSmoothAsyncTo(90.0f, 500);
    	servo.awaitMoveCompleted();
    	
    	servo.moveTo(0.0f);
    	
    	servo.clearServoListeners();
    }
}
