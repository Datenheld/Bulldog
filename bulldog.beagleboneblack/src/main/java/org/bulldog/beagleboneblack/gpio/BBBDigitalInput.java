package org.bulldog.beagleboneblack.gpio;

import org.bulldog.beagleboneblack.BeagleBonePin;
import org.bulldog.beagleboneblack.jni.NativeGpio;
import org.bulldog.beagleboneblack.sysfs.SysFsPin;
import org.bulldog.core.Edge;
import org.bulldog.core.Signal;
import org.bulldog.core.gpio.Pin;
import org.bulldog.core.gpio.base.AbstractDigitalInput;
import org.bulldog.core.gpio.event.InterruptListener;

public class BBBDigitalInput extends AbstractDigitalInput {

	private static final int MAX_DEBOUNCE_COUNT = 10;
	
	private BBBPinInterruptControl interruptControl;
	private SysFsPin sysFsPin;
	private boolean areInterruptsEnabled = true;
	
	public BBBDigitalInput(Pin pin) {
		super(pin);
		sysFsPin = new SysFsPin(getPin().getAddress());
		interruptControl = new BBBPinInterruptControl(this, sysFsPin.getValueFilePath());
	}

	public void enableInterrupts() {
		if(getInterruptListeners().size() > 0 && !interruptControl.isRunning()) {
			interruptControl.start();
		}
		areInterruptsEnabled = true;
	}

	public void disableInterrupts() {
		if(interruptControl.isRunning()) {
			interruptControl.stop();
		}
		areInterruptsEnabled = false;
	}
	
	public boolean areInterruptsEnabled() {
		return areInterruptsEnabled;
	}

	public Signal readSignal() {
		BeagleBonePin bbbPin = (BeagleBonePin)getPin();
		return NativeGpio.digitalRead(bbbPin.getPort(), bbbPin.getIndexOnPort()) > 0 ? Signal.High : Signal.Low;
	}

	public void shutdown() {
		interruptControl.shutdown();
	}
	
	@Override
	public void addInterruptListener(InterruptListener listener) {
		super.addInterruptListener(listener);
		if(areInterruptsEnabled() && !interruptControl.isRunning()) {
			interruptControl.start();
		}
	}
	
	@Override
	public void removeInterruptListener(InterruptListener listener) {
		super.removeInterruptListener(listener);
		if(getInterruptListeners().size() == 0) {
			interruptControl.stop();
		}
	}
	
	@Override
	public void clearInterruptListeners() {
		super.clearInterruptListeners();
		interruptControl.stop();
	}

	public Signal readSignalDebounced(int debounceTime) {
		long startTime = System.currentTimeMillis();
		long delta = 0;
		Signal currentState = readSignal();
		int counter = 0;
		while (delta < debounceTime) {
			Signal reading = readSignal();

			if (reading == currentState && counter > 0) {
				counter--;
			}

			if (reading != currentState) {
				counter++;
			}

			if (counter >= MAX_DEBOUNCE_COUNT) {
				counter = 0;
				currentState = reading;
				return currentState;
			}

			delta = System.currentTimeMillis() - startTime;
		}

		return currentState;
	}


	public void setup() {
		exportPinIfNecessary();
		interruptControl.setup();
		
		BeagleBonePin bbbPin = (BeagleBonePin)getPin();
		NativeGpio.pinMode(bbbPin.getPort(), bbbPin.getIndexOnPort(), NativeGpio.DIRECTION_IN);
	}

	public void teardown() {
		disableInterrupts();
		unexportPin();
	}
	
	private void exportPinIfNecessary() {
		sysFsPin.exportIfNecessary();
		sysFsPin.setDirection("in");
		sysFsPin.setEdge(getInterruptTrigger().toString().toLowerCase());
	}
	
	private void unexportPin() {
		sysFsPin.unexport();
	}

	@Override
	protected void setInterruptDebounceTimeImpl(int ms) {
		interruptControl.setDebounceMilliseconds(ms);
	}

	@Override
	protected void setInterruptTriggerImpl(Edge edge) {
		sysFsPin.setEdge(getInterruptTrigger().toString().toLowerCase());
	}

}
