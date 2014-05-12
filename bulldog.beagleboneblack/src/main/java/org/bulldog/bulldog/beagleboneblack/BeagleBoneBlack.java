package org.bulldog.bulldog.beagleboneblack;

import org.bulldog.beagleboneblack.bus.BBBI2cBus;
import org.bulldog.beagleboneblack.gpio.BBBAnalogInput;
import org.bulldog.beagleboneblack.gpio.BBBDigitalInput;
import org.bulldog.beagleboneblack.gpio.BBBDigitalOutput;
import org.bulldog.beagleboneblack.gpio.BBBPwm;
import org.bulldog.core.AbstractBoard;
import org.bulldog.core.gpio.Pin;
import org.bulldog.core.gpio.PinFeature;
import org.bulldog.core.gpio.base.AbstractPwm;
import org.bulldog.core.gpio.event.ActivationEventArgs;
import org.bulldog.core.gpio.event.ActivationListener;

public class BeagleBoneBlack extends AbstractBoard implements ActivationListener {
	
	static {
		System.loadLibrary("bulldog");
	}
	
	private static final String NAME = "BeagleBone Black";
		
	public BeagleBoneBlack() {
		createPins();
		createBuses();
		createShutdownHook();
	}
	
	private void createPins() {
		System.out.println("CREATE DIGITAL PINS");
		getPins().add(createDigitalIOPin("P8_3", 	"GPIO1_6",	1,  6, 	8, 	 3));
		getPins().add(createDigitalIOPin("P8_4", 	"GPIO1_7", 	1,  7,  8, 	 4));
		getPins().add(createDigitalIOPin("P8_5", 	"GPIO1_2", 	1,  2,  8, 	 5));
		getPins().add(createDigitalIOPin("P8_6", 	"GPIO1_3", 	1,  3,  8,	 6));
		getPins().add(createDigitalIOPin("P8_7", 	"TIMER4",  	2,  2,	8,	 7));
		getPins().add(createDigitalIOPin("P8_8", 	"TIMER7",  	2,  3, 	8,	 8));
		getPins().add(createDigitalIOPin("P8_9", 	"TIMER5",  	2,  5,  8,	 9));
		getPins().add(createDigitalIOPin("P8_10", 	"TIMER6",  	2,  4, 	8,	10));
		getPins().add(createDigitalIOPin("P8_11", 	"GPIO1_13", 1, 13,	8,	11));
		getPins().add(createDigitalIOPin("P8_12", 	"GPIO1_12", 1, 12,	8,	12));
		getPins().add(createDigitalIOPin("P8_13", 	"EHRPWM2B", 0, 23,	8,	13));
		getPins().add(createDigitalIOPin("P8_14", 	"GPIO0_26", 0, 26,	8,	14));
		getPins().add(createDigitalIOPin("P8_15", 	"GPIO1_15", 1, 15,	8,	15));
		getPins().add(createDigitalIOPin("P8_16", 	"GPIO1_14", 1, 14,	8,	16));
		getPins().add(createDigitalIOPin("P8_17", 	"GPIO0_27", 0, 27,	8,	17));
		getPins().add(createDigitalIOPin("P8_18", 	"GPIO2_1", 	2,  1,	8,	18));
		getPins().add(createDigitalIOPin("P8_19", 	"EHRPWM2A", 0, 22,	8,	19));
		getPins().add(createDigitalIOPin("P8_20", 	"GPIO1_31", 1, 31,	8,	20));
		System.out.println("CREATEd DIGITAL PINS");
		
		
		getPins().add(createDigitalIOPin("P9_29",	"EHRPWM0B",	3,	25,	9,	29));
		getPins().add(createDigitalIOPin("P9_30",	"GPIO3_26",	3,	26,	9,	30));
		getPins().add(createDigitalIOPin("P9_31",	"EHRPWM0A",	3,	24,	9,	31));
		
		getPins().add(createAnalogInputPin("P9_33",	"AIN4",		2,	7,	9,	33, 	4));
		getPins().add(createAnalogInputPin("P9_35",	"AIN6",		2,	9,	9,	35,		6));
		getPins().add(createAnalogInputPin("P9_36",	"AIN5",		2,	8,	9,	36,		5));
		getPins().add(createAnalogInputPin("P9_37",	"AIN2",		2,	5,	9,	37,		2));
		getPins().add(createAnalogInputPin("P9_38",	"AIN3",		2,	6,	9,	38,		3));
		getPins().add(createAnalogInputPin("P9_39",	"AIN0",		2,	3,	9,	39,		0));
		getPins().add(createAnalogInputPin("P9_40",	"AIN1",		2,	4,	9,	40,		1));
		
		addPwmToPin(getPinByName("P8_13"));
		addPwmToPin(getPinByName("P8_19"));
		addPwmToPin(getPinByName("P9_29"));
		addPwmToPin(getPinByName("P9_31"));
	}

	private Pin createDigitalIOPin(String name, String internalName, int bank, int pinIndex, int port, int portIndex) {
		BeagleBonePin pin = new BeagleBonePin(name, internalName, bank, pinIndex, port, portIndex);
		pin.addFeature(new BBBDigitalOutput(pin))
		   .addFeature(new BBBDigitalInput(pin));
		return pin;
	}
	
	private void addPwmToPin(Pin pin) {
		pin.addFeature(new BBBPwm(pin));
		pin.addActivationListener(this);
	}
	
	private Pin createAnalogInputPin(String name, String internalName, int bank, int pinIndex, int port, int portIndex, int channelId) {
		BeagleBonePin pin = new BeagleBonePin(name, internalName, bank, pinIndex, port, portIndex);
		pin.addFeature(new BBBAnalogInput(pin, channelId));
		return pin;
	}
	

	private void createBuses() {
		getI2cBuses().add(new BBBI2cBus("/dev/i2c-0"));
		getI2cBuses().add(new BBBI2cBus("/dev/i2c-1"));
	}


	@Override
	public String getName() {
		return NAME;
	}

	public void featureActivating(Object o, ActivationEventArgs args) {
		PinFeature feature = args.getPinFeature();
		Pin pin = feature.getPin();
		
		
		if(feature instanceof AbstractPwm) {
			if(pin.getAddress() == 23) {
				if(getPinByAddress(22).getActiveFeature() instanceof AbstractPwm) {
					throw new UnsupportedOperationException("You can have either EHRPWM2A or EHRPWM2B activated, not both :-(");
				}
			}
			
			if(pin.getAddress() == 22) {
				if(getPinByAddress(23).getActiveFeature() instanceof AbstractPwm) {
					throw new UnsupportedOperationException("You can have either EHRPWM2A or EHRPWM2B activated, not both :-(");
				}
			}	
		}
	}

	public void featureActivated(Object o, ActivationEventArgs args) {
		
	}

	public void featureDeactivating(Object o, ActivationEventArgs args) {
	}

	public void featureDeactivated(Object o, ActivationEventArgs args) {
	}

	public void cleanup() {
		for(Pin pin : this.getPins()) {
			if(pin.getActiveFeature() == null) { continue; }
			pin.getActiveFeature().teardown();
		}
	}
	
	public void createShutdownHook() {
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				cleanup();
			}
		});
	}
}
