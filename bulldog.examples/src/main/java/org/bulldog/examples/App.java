package org.bulldog.examples;

import java.io.IOException;

import org.bulldog.beagleboneblack.BBBNames;
import org.bulldog.core.Signal;
import org.bulldog.core.gpio.DigitalInput;
import org.bulldog.core.gpio.Pwm;
import org.bulldog.core.io.serial.SerialDataEventArgs;
import org.bulldog.core.io.serial.SerialDataListener;
import org.bulldog.core.io.serial.SerialPort;
import org.bulldog.core.platform.Board;
import org.bulldog.core.platform.Platform;
import org.bulldog.core.util.BulldogUtil;
import org.bulldog.devices.servo.Servo;
import org.bulldog.devices.servo.TowerProMicroSG90;
import org.bulldog.devices.switches.Button;
import org.bulldog.devices.switches.ButtonListener;
import org.bulldog.devices.switches.IncrementalRotaryEncoder;
import org.bulldog.devices.switches.RotaryEncoderListener;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main(String[] args) throws IOException
    {
    	final Board board = Platform.createBoard();
    	
    	//I2CLcd lcd = new I2CLcd(board.getI2cBuses().get(1), 0x20);
    	//lcd.write("Hello World");
    	SerialPort serial = board.getSerialPort(BBBNames.UART2);
    	serial.setBaudRate(9600);
    	serial.setBlocking(false);
    	serial.open();
    	serial.addListener(new SerialDataListener() {

			@Override
			public void onSerialDataAvailable(SerialDataEventArgs args) {
				System.out.print(BulldogUtil.bytesToString(args.getData()));
			}
    		
    	});
    	
    	
    	final DigitalInput interrupt = board.getPin("P8", 11).as(DigitalInput.class);
    	final DigitalInput interrupt2 = board.getPin("P8", 12).as(DigitalInput.class);
    	final IncrementalRotaryEncoder encoder = new IncrementalRotaryEncoder(interrupt, interrupt2);
    	
    	
    	
    	final DigitalInput buttonInput = board.getPin("P8", 14).as(DigitalInput.class);
    	Button button = new Button(buttonInput, Signal.High);
    	button.addListener(new ButtonListener() {

			public void buttonPressed() {
				System.out.println("button pressed");
			}

			public void buttonReleased() {
				System.out.println("button released");
				
			}
    		
    	});
    	
    	final Pwm pwm = board.getPin("P9", 14).as(Pwm.class);
    	final Servo servo = new TowerProMicroSG90(pwm);
    	
    	encoder.addListener(new RotaryEncoderListener() {
			public void valueChanged(Integer oldValue, Integer newValue) {
				System.out.println(newValue);
				if(oldValue > newValue) {
					System.out.println("ERROR ERROR ERROR!");
				}
			}

			public void turnedClockwise() {
				servo.setAngle(servo.getAngle() + 1.0f);
			}

			public void turnedCounterclockwise() {
				servo.setAngle(servo.getAngle() - 1.0f);
			}
    		
    	});
    	    	
    	
    	while(true) {
    		BulldogUtil.sleepMs(100);
    	}
    	
    }
}
