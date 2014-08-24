package org.bulldog.devices.portexpander;

import java.io.IOException;

import org.bulldog.core.Edge;
import org.bulldog.core.Signal;
import org.bulldog.core.io.bus.i2c.I2cBus;
import org.bulldog.core.io.bus.i2c.I2cConnection;
import org.bulldog.core.pinfeatures.DigitalIO;
import org.bulldog.core.pinfeatures.DigitalInput;
import org.bulldog.core.pinfeatures.Pin;
import org.bulldog.core.pinfeatures.event.InterruptEventArgs;
import org.bulldog.core.pinfeatures.event.InterruptListener;
import org.bulldog.core.platform.AbstractPinProvider;
import org.bulldog.core.util.BitMagic;

/**
 * This class represents the popular PCF8574(A) I2C Port Expander family.
 * It supports all the features of the PCF8574, including interrupts
 * if you connect the INT signal to a DigitalInput of your board.
 * 
 * You can use all the pins as if they are normal pins or you can read
 * or write the state of all pins directly with the readState/writeState
 * methods.
 * 
 * @author Datenheld
 *
 */
public class PCF8574 extends AbstractPinProvider implements InterruptListener {

	public static final String P0 = "P0";
	public static final String P1 = "P1";
	public static final String P2 = "P2";
	public static final String P3 = "P3";
	public static final String P4 = "P4";
	public static final String P5 = "P5";
	public static final String P6 = "P5";
	public static final String P7 = "P5";
	
	private I2cConnection connection;
	private DigitalInput interrupt;
	
	private int state = 0xFF;
	
	public PCF8574(I2cConnection connection) throws IOException {
		this(connection, null);
	}

	public PCF8574(I2cBus bus, int address) throws IOException {
		this(bus.createI2cConnection(address), null);
	}
	
	public PCF8574(I2cBus bus, int address, DigitalInput interrupt) throws IOException {
		this(bus.createI2cConnection(address), interrupt);
	}
	
	public PCF8574(I2cConnection connection, DigitalInput interrupt) throws IOException {
		createPins();
		this.connection = connection;
		setInterrupt(interrupt);
	}
	
	private void createPins() {
		for(int i = 0; i <= 7; i++) {
			Pin pin = new Pin("P" + i, i, "P", i);
			pin.addFeature(new PCF8574DigitalInput(pin, this));
			pin.addFeature(new PCF8574DigitalOutput(pin, this));
			getPins().add(pin);
		}
	}

	private void handleInterruptEvent(DigitalInput input, Signal oldState, Signal currentState) {
		if(!input.areInterruptsEnabled()) { return; }
		
		Edge edge = determineInterruptEdge(oldState, currentState);
		if(!isInterruptTrigger(input, edge)) { return; }
		
		input.fireInterruptEvent(new InterruptEventArgs(input.getPin(), edge));
	}
		
	private boolean isInterruptTrigger(DigitalInput input, Edge edge) {
		return edge == input.getInterruptTrigger() || input.getInterruptTrigger() == Edge.Both;
	}
	
	@Override
	public void interruptRequest(InterruptEventArgs args) {
		byte lastKnownState = (byte) state;
		byte readState = readState();
		for(int i = 0; i <= 7; i++) {
			Pin currentPin = getPin(i);
			
			if(!currentPin.isFeatureActive(DigitalInput.class)) { continue; }
			if(currentPin.isFeatureActive(DigitalIO.class)) {
				DigitalIO io = currentPin.getFeature(DigitalIO.class);
				if(!io.isInputActive()) {
					continue;
				}
			}
			
			DigitalInput input = currentPin.as(DigitalInput.class);
			int lastKnownPinState = BitMagic.getBit(lastKnownState, currentPin.getAddress());
			int currentState =  BitMagic.getBit(readState, currentPin.getAddress());
			if(lastKnownPinState == currentState) { continue; }
			handleInterruptEvent(input, Signal.fromNumericValue(lastKnownState), Signal.fromNumericValue(currentState));
		}
	}
	
	private Edge determineInterruptEdge(Signal oldState, Signal currentState) {
		if(currentState == Signal.Low && oldState == Signal.High) { return Edge.Falling; }
		return Edge.Rising;
	}
	
	public byte getState() {
		return (byte)state;
	}
		
	public void writeState(int state) {
		this.state = state;
		try {
			connection.writeByte(state);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This method preservers the outputs' state and adjust
	 * the state of the inputs.
	 * 
	 * Before it reads, it sets all the input pins that
	 * are to be read to high, according to the PCF8574
	 * datasheet.
	 * 
	 * @return the state read from the PCF857
	 */
	public byte readState() {
		try {
			byte buffer = getState();
			switchInputsHigh();
			byte readByte = connection.readByte();
			buffer = applyReadInputs(readByte, buffer);
			writeState(buffer);
			return readByte;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return (byte)0;
	}
	
	private void switchInputsHigh() {
		byte highInputState = getState();
		for(Pin pin : getPins()) {
			if(pin.isFeatureActive(DigitalInput.class)){
				highInputState = BitMagic.setBit(highInputState, pin.getAddress(), 1);
			}
		}
		
		writeState(highInputState);
	}
	
	private byte applyReadInputs(byte b, byte buffer)  {
		for(Pin pin : getPins()) {
			if(pin.isFeatureActive(DigitalInput.class)){
				buffer = BitMagic.setBit(buffer, pin.getAddress(), BitMagic.getBit(b, pin.getAddress()));
			}
		}
		
		return buffer;
	}
	
	public void setInterrupt(DigitalInput input) {
		if(interrupt != null) {
			interrupt.removeInterruptListener(this);
		}
		
		interrupt = input;
		if(interrupt != null) {
			interrupt.addInterruptListener(this);
		}
	}
}
