package org.bulldog.devices.switches;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bulldog.core.Edge;
import org.bulldog.core.Signal;
import org.bulldog.core.gpio.DigitalInput;
import org.bulldog.core.gpio.event.InterruptEventArgs;
import org.bulldog.core.gpio.event.InterruptListener;

public class IncrementalRotaryEncoder {

	private DigitalInput interruptSignalA;
	private DigitalInput interruptSignalB;
	private boolean signalA = false;
	private boolean signalB = false;
	private volatile int position = 0;
	private List<RotaryEncoderListener> listeners = Collections.synchronizedList(new ArrayList<RotaryEncoderListener>());

	public IncrementalRotaryEncoder(DigitalInput signalA, DigitalInput signalB) {
		this.interruptSignalA = signalA;
		this.interruptSignalB = signalB;

		initializeClockwiseInterrupt(signalA);
		initializeCounterClockwiseInterrupt(signalB);
	}

	private void initializeCounterClockwiseInterrupt(DigitalInput counterClockwise) {
		this.interruptSignalA.setInterruptTrigger(Edge.Both);
		counterClockwise.addInterruptListener(new InterruptListener() {

			@Override
			public void interruptRequest(InterruptEventArgs args) {
				signalB = args.getEdge() == Edge.Rising;
				if (signalB && !(interruptSignalA.read() == Signal.High)) {
					position--;
					fireValueChanged(position - 1, position);
					fireCounterclockwiseTurn();
				}
			}

		});
	}

	private void initializeClockwiseInterrupt(DigitalInput clockwise) {
		this.interruptSignalB.setInterruptTrigger(Edge.Both);
		clockwise.addInterruptListener(new InterruptListener() {

			@Override
			public void interruptRequest(InterruptEventArgs args) {
				signalA = args.getEdge() == Edge.Rising;
				if (signalA && !(interruptSignalB.read() == Signal.High)) {
					position++;
					fireValueChanged(position - 1, position);
					fireClockwiseTurn();
				}
			}

		});
	}

	public int getValue() {
		return position;
	}

	public void addListener(RotaryEncoderListener listener) {
		this.listeners.add(listener);
	}

	public void removeListener(RotaryEncoderListener listener) {
		this.listeners.remove(listener);
	}

	public void clearListeners() {
		this.listeners.clear();
	}

	protected void fireValueChanged(int oldValue, int newValue) {
		synchronized(listeners) {
			for (RotaryEncoderListener listener : listeners) {
				listener.valueChanged(oldValue, newValue);
			}
		}
	}

	protected void fireClockwiseTurn() {
		synchronized(listeners) {
			for (RotaryEncoderListener listener : listeners) {
				listener.turnedClockwise();
			}
		}
	}

	protected void fireCounterclockwiseTurn() {
		synchronized(listeners) {
			for (RotaryEncoderListener listener : listeners) {
				listener.turnedCounterclockwise();
			}
		}
	}

	protected void setPosition(int position) {
		int oldPosition = this.position;
		this.position = position;
		fireValueChanged(oldPosition, position);
	}

}
