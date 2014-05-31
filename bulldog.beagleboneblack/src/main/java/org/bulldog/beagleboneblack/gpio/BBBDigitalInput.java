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

	private BBBPinInterruptControl interruptControl;
	private SysFsPin sysFsPin;
	
	public BBBDigitalInput(Pin pin) {
		super(pin);
		sysFsPin = new SysFsPin(getPin().getAddress());
		interruptControl = new BBBPinInterruptControl(this, sysFsPin.getValueFilePath());
	}
	
	public Signal read() {
		BeagleBonePin bbbPin = (BeagleBonePin)getPin();
		return NativeGpio.digitalRead(bbbPin.getPortNumeric(), bbbPin.getIndexOnPort()) > 0 ? Signal.High : Signal.Low;
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
	
	protected void enableInterruptsImpl() {
		if(getInterruptListeners().size() > 0 && !interruptControl.isRunning()) {
			interruptControl.start();
		}
	}

	protected void disableInterruptsImpl() {
		if(interruptControl.isRunning()) {
			interruptControl.stop();
		}
	}

	public void setup() {
		exportPinIfNecessary();
		interruptControl.setup();
		
		BeagleBonePin bbbPin = (BeagleBonePin)getPin();
		NativeGpio.pinMode(bbbPin.getPortNumeric(), bbbPin.getIndexOnPort(), NativeGpio.DIRECTION_IN);
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
