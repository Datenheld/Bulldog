/*
 * (C) Copyright 2014 libbulldog (http://libbulldog.org/) and others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package org.bulldog.core.gpio;

import java.util.List;

import org.bulldog.core.Edge;
import org.bulldog.core.Signal;
import org.bulldog.core.gpio.event.InterruptEventArgs;
import org.bulldog.core.gpio.event.InterruptListener;

/**
 * This interface specified the operations that can be used on a Pin that is
 * configured as a digital input. This includes reading the current state on
 * the pin as well as handling interrupt events.
 */
public interface DigitalInput extends PinFeature {

	/**
	 * Reads the current signal on the pin.
	 *
	 * @return the signal - either low or high
	 */
	Signal read();
	
	/**
	 * Reads the signal on the pin debounced. This is useful if one needs
	 * to read a bouncing state transition. It does so by continuously
	 * reading the input and after it had a number of stable reads it
	 * returns the read value.
	 *
	 * @param debounceMilliseconds this is the time that is used for debouncing 
	 * 		  in the worst case. When the input is still bouncing after the 
	 * 		  specified period, then the return value will not be reliable.
	 * @return the signal that was read from the pin - either low or high
	 */
	Signal readDebounced(int debounceMilliseconds);
	
	/**
	 * This method disables interrupt events on this input.
	 * When interrupt events are disabled, then edges on the pins won't trigger
	 * the {@link InterruptListener InterruptListener's} 
	 * {@link InterruptListener#interruptRequest(InterruptEventArgs) interruptRequest}
	 * method.
	 */
	void disableInterrupts();
	
	/**
	 * This method enables interrupt events on this input.
	 */
	void enableInterrupts();
	
	/**
	 * Are interrupts enabled.
	 *
	 * @return true, if successful
	 */
	boolean areInterruptsEnabled();
	
	/**
	 * Sets the interrupt debounce ms.
	 *
	 * @param milliSeconds the new interrupt debounce ms
	 */
	void setInterruptDebounceMs(int milliSeconds);
	
	/**
	 * Gets the interrupt debounce ms.
	 *
	 * @return the interrupt debounce ms
	 */
	int getInterruptDebounceMs();
	
	/**
	 * Sets the interrupt trigger.
	 *
	 * @param edge the new interrupt trigger
	 */
	void setInterruptTrigger(Edge edge);
	
	/**
	 * Gets the interrupt trigger.
	 *
	 * @return the interrupt trigger
	 */
	Edge getInterruptTrigger();
	
	/**
	 * Fire interrupt event.
	 *
	 * @param args the args
	 */
	void fireInterruptEvent(InterruptEventArgs args);
	
	/**
	 * Adds the interrupt listener.
	 *
	 * @param listener the listener
	 */
	void addInterruptListener(InterruptListener listener);
	
	/**
	 * Removes the interrupt listener.
	 *
	 * @param listener the listener
	 */
	void removeInterruptListener(InterruptListener listener);
	
	/**
	 * Clear interrupt listeners.
	 */
	void clearInterruptListeners();
	
	/**
	 * Gets the interrupt listeners.
	 *
	 * @return the interrupt listeners
	 */
	List<InterruptListener> getInterruptListeners();

}