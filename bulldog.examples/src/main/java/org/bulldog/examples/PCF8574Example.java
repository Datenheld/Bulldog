package org.bulldog.examples;

import java.io.IOException;

import org.bulldog.beagleboneblack.BBBNames;
import org.bulldog.core.Signal;
import org.bulldog.core.gpio.DigitalInput;
import org.bulldog.core.gpio.DigitalOutput;
import org.bulldog.core.gpio.event.InterruptEventArgs;
import org.bulldog.core.gpio.event.InterruptListener;
import org.bulldog.core.platform.Board;
import org.bulldog.core.platform.Platform;
import org.bulldog.core.util.BulldogUtil;
import org.bulldog.devices.portexpander.PCF8574;
import org.bulldog.devices.switches.Button;
import org.bulldog.devices.switches.ButtonListener;

public class PCF8574Example {

	public static void main(String... args) throws IOException {
		
		final Board board = Platform.createBoard();
		DigitalInput expanderInterrupt = board.getPin(BBBNames.P8_12).as(DigitalInput.class);
		
		int expanderAddress = 0x24;
		PCF8574 portExpander = new PCF8574(board.getI2cBus(BBBNames.I2C_1), expanderAddress, expanderInterrupt);
		
		DigitalInput p0 = portExpander.getPin(PCF8574.P0).as(DigitalInput.class);
		p0.addInterruptListener(new InterruptListener() {

			@Override
			public void interruptRequest(InterruptEventArgs args) {
				System.out.println("INTERRUPT P0: " + args.getEdge());
			}
			
		});
		
		Button button = new Button(portExpander.getPin(PCF8574.P1).as(DigitalInput.class), Signal.High);
		button.addListener(new ButtonListener() {

			@Override
			public void buttonPressed() {
				System.out.println("PRESSED");
			}

			@Override
			public void buttonReleased() {
				System.out.println("RELEASED");
			}
			
		});
		
		DigitalOutput output = portExpander.getPin(PCF8574.P2).as(DigitalOutput.class);
		DigitalOutput output2 = portExpander.getPin(PCF8574.P3).as(DigitalOutput.class);
		
		while(true) {
			output.toggle();
			output2.toggle();
			BulldogUtil.sleepMs(100);
		}
		
	}
	
}
