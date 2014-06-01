package org.bulldog.beagleboneblack.gpio;

import org.bulldog.beagleboneblack.BeagleBonePin;
import org.bulldog.beagleboneblack.jni.NativeGpio;
import org.bulldog.beagleboneblack.sysfs.SysFsPin;
import org.bulldog.core.Edge;
import org.bulldog.core.Signal;
import org.bulldog.core.gpio.Pin;
import org.bulldog.core.gpio.base.AbstractDigitalInput;
import org.bulldog.core.gpio.event.InterruptEventArgs;
import org.bulldog.core.gpio.event.InterruptListener;
import org.bulldog.linux.io.LinuxEpollListener;
import org.bulldog.linux.io.LinuxEpollThread;
import org.bulldog.linux.jni.NativePollResult;

public class BBBDigitalInput extends AbstractDigitalInput implements LinuxEpollListener {

	private LinuxEpollThread interruptControl;
	private SysFsPin sysFsPin;
	private Edge lastEdge;
	private volatile long lastInterruptTime;
	
	public BBBDigitalInput(Pin pin) {
		super(pin);
		sysFsPin = new SysFsPin(getPin().getAddress());
		interruptControl = new LinuxEpollThread(sysFsPin.getValueFilePath());
		interruptControl.addListener(this);
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
	public void setInterruptTrigger(Edge edge) {
		super.setInterruptTrigger(edge);
		sysFsPin.setEdge(getInterruptTrigger().toString().toLowerCase());
	}

	@Override
	public void processEpollResults(NativePollResult[] results) {
		for(NativePollResult result : results) {
			Edge edge = getEdge(result);
			if(lastEdge != null && lastEdge.equals(edge)) { continue; }
			
			long delta = System.currentTimeMillis() - lastInterruptTime;
			if(delta <= this.getInterruptDebounceMs()) {
				continue;
			}
			
			lastInterruptTime = System.currentTimeMillis();
			lastEdge = edge;
			fireInterruptEvent(new InterruptEventArgs(getPin(), edge));
		}
	}
	
	private Edge getEdge(NativePollResult result) {
		if(result.getData() == null) { return null; }
		if(result.getDataAsString().charAt(0) == '1') {
			return Edge.Rising;
		} 
		
		return Edge.Falling;
	}

}
