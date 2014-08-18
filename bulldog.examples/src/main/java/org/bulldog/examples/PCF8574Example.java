package org.bulldog.examples;

import java.io.IOException;

import org.bulldog.beagleboneblack.BBBNames;
import org.bulldog.core.Signal;
import org.bulldog.core.gpio.DigitalInput;
import org.bulldog.core.gpio.DigitalOutput;
import org.bulldog.core.gpio.event.InterruptEventArgs;
import org.bulldog.core.gpio.event.InterruptListener;
import org.bulldog.core.io.bus.i2c.I2cBus;
import org.bulldog.core.platform.Board;
import org.bulldog.core.platform.Platform;
import org.bulldog.core.util.BitMagic;
import org.bulldog.core.util.BulldogUtil;
import org.bulldog.devices.portexpander.PCF8574;
import org.bulldog.devices.switches.Button;
import org.bulldog.devices.switches.ButtonListener;

public class PCF8574Example {

	public static void main(String... args) throws IOException {
		
		//Grab your platform
		final Board board = Platform.createBoard();
		
		//If we want to use interrupts on the PCF8574, we need to connect
		//its INT pin to a DigitalInput on the board
		DigitalInput expanderInterrupt = board.getPin(BBBNames.P8_12).as(DigitalInput.class);
		
		//Obtain the I2C bus of the board and create the expander.
		//Just pass the bus and the address of the PCF8574
		I2cBus bus = board.getI2cBus(BBBNames.I2C_1);
		int expanderAddress = 0x24;
		PCF8574 portExpander = new PCF8574(bus, expanderAddress, expanderInterrupt);
		
		//We will use P0 on the PCF8574 as an interrupt
		DigitalInput p0 = portExpander.getPin(PCF8574.P0).as(DigitalInput.class);
		p0.addInterruptListener(new InterruptListener() {

			@Override
			public void interruptRequest(InterruptEventArgs args) {
				System.out.println("INTERRUPT P0: " + args.getEdge());
			}
			
		});
		
		//On P1 of the PCF8574 we will put a button that is 
		//in pressed state when the signal is high
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
		
		//You can set the state of the expander directly
		//if you don't like to mess with pins
		portExpander.writeState((byte)0xff);
		BulldogUtil.sleepMs(1000);
		
		//You can also read it directly if desired.
		//Notice that our two inputs will be low.
		byte state = portExpander.readState();
		System.out.println("The current state of the expander is: " + BitMagic.toBitString(state));
		
		//Finally, we get two DigitalOutputs from the expander,
		//which will be toggled indefinitely.
		DigitalOutput p2 = portExpander.getPin(PCF8574.P2).as(DigitalOutput.class);
		DigitalOutput p3 = portExpander.getPin(PCF8574.P3).as(DigitalOutput.class);
		
		//Blink every second
		p3.startBlinking(1000);
		
		while(true) {
			p2.toggle();
			BulldogUtil.sleepMs(100);
		}
		
	}
	
}
