package org.bulldog.beagleboneblack;

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
		getPins().add(createDigitalIOPin("P8_21", 	"GPIO1_30",	1, 30,  8, 	21));
		getPins().add(createDigitalIOPin("P8_22", 	"GPIO1_5", 	1,  5,  8, 	22));
		getPins().add(createDigitalIOPin("P8_23", 	"GPIO1_4",	1,  4, 	8, 	23));
		getPins().add(createDigitalIOPin("P8_24", 	"GPIO1_1", 	1,  1,  8, 	24));
		getPins().add(createDigitalIOPin("P8_25", 	"GPIO1_0",	1,  0, 	8, 	25));
		getPins().add(createDigitalIOPin("P8_26", 	"GPIO1_29", 1,  29, 8, 	26));
		getPins().add(createDigitalIOPin("P8_27", 	"GPIO2_22",	2,  22, 8, 	27));
		getPins().add(createDigitalIOPin("P8_28", 	"GPIO2_24", 2,  24, 8, 	28));
		getPins().add(createDigitalIOPin("P8_29", 	"GPIO2_23",	2,  23, 8, 	29));
		getPins().add(createDigitalIOPin("P8_30", 	"GPIO2_25", 2,  25, 8, 	30));
		getPins().add(createDigitalIOPin("P8_31", 	"GPIO0_10",	0,  10,	8, 	31));
		getPins().add(createDigitalIOPin("P8_32", 	"GPIO0_11",	0,  11, 8,	32));
		getPins().add(createDigitalIOPin("P8_33", 	"GPIO0_9",	0,   9,	8, 	33));
		getPins().add(createDigitalIOPin("P8_34", 	"GPIO2_17",	2,  17, 8, 	34));
		getPins().add(createDigitalIOPin("P8_35", 	"GPIO0_8",	0,   8,	8, 	35));
		getPins().add(createDigitalIOPin("P8_36", 	"GPIO2_16",	2,  16, 8, 	36));
		getPins().add(createDigitalIOPin("P8_37", 	"GPIO2_14",	2,  14,	8, 	37));
		getPins().add(createDigitalIOPin("P8_38", 	"GPIO2_1", 	2,   1, 8, 	38));
		getPins().add(createDigitalIOPin("P8_39", 	"GPIO2_12",	2,  12,	8, 	39));
		getPins().add(createDigitalIOPin("P8_40", 	"GPIO2_13",	2,  14, 8, 	40));
		getPins().add(createDigitalIOPin("P8_41", 	"GPIO2_10",	2,  10,	8, 	41));
		getPins().add(createDigitalIOPin("P8_42", 	"GPIO2_11",	2,  11, 8, 	42));
		getPins().add(createDigitalIOPin("P8_43", 	"GPIO2_8",	2,   8,	8, 	43));
		getPins().add(createDigitalIOPin("P8_44", 	"GPIO2_9", 	2,   9, 8, 	44));
		getPins().add(createDigitalIOPin("P8_45", 	"GPIO2_6",	2,   6,	8, 	45));
		getPins().add(createDigitalIOPin("P8_46", 	"GPIO2_27",	2,  27, 8, 	46));
		
		getPins().add(createDigitalIOPin("P9_11", 	"UART4_RX", 0, 30,	9,	11));
		getPins().add(createDigitalIOPin("P9_12", 	"GPIO1_28", 1, 28,	9,	12));
		getPins().add(createDigitalIOPin("P9_13", 	"UART4_TX", 0, 31,	9,	13));
		getPins().add(createDigitalIOPin("P9_14", 	"EHRPWM1A", 1, 18,	9,	14));
		getPins().add(createDigitalIOPin("P9_15", 	"GPIO1_16", 1, 16,	9,	15));
		getPins().add(createDigitalIOPin("P9_16", 	"EHRPWM1B", 1, 19,	9,	16));
		getPins().add(createDigitalIOPin("P9_17", 	"GPIO0_5",  0,  5,	9,	17));
		getPins().add(createDigitalIOPin("P9_18", 	"GPIO0_4", 	0,  4,	9,	18));
		getPins().add(createDigitalIOPin("P9_19", 	"GPIO0_15", 0, 15,	9,	19));
		getPins().add(createDigitalIOPin("P9_20", 	"GPIO0_14", 0, 14,	9,	20));
		getPins().add(createDigitalIOPin("P9_21", 	"UART2TX",	0,  3,  9, 	21));
		getPins().add(createDigitalIOPin("P9_22", 	"UART2RX", 	0,  2,  9, 	22));
		getPins().add(createDigitalIOPin("P9_23", 	"GPIO1_17",	1,  17,	9, 	23));
		getPins().add(createDigitalIOPin("P9_24", 	"UART1TX", 	0,  12, 9, 	24));
		getPins().add(createDigitalIOPin("P9_25", 	"GPIO3_21",	3,  21, 9, 	25));
		getPins().add(createDigitalIOPin("P9_26", 	"UART1RX",  0,  13, 9, 	26));
		getPins().add(createDigitalIOPin("P9_27", 	"GPIO3_19",	3,  19, 9, 	27));
		getPins().add(createDigitalIOPin("P9_28", 	"GPIO3_17", 3,  17, 9, 	28));
		getPins().add(createDigitalIOPin("P9_29",	"GPIO3_15",	3,	15,	9,	29));
		getPins().add(createDigitalIOPin("P9_30",	"GPIO3_16",	3,	16,	9,	30));
		getPins().add(createDigitalIOPin("P9_31",	"EHRPWM0A",	3,	14,	9,	31));
		
		getPins().add(createAnalogInputPin("P9_33",	"AIN4",		2,	7,	9,	33, 	4));
		getPins().add(createAnalogInputPin("P9_35",	"AIN6",		2,	9,	9,	35,		6));
		getPins().add(createAnalogInputPin("P9_36",	"AIN5",		2,	8,	9,	36,		5));
		getPins().add(createAnalogInputPin("P9_37",	"AIN2",		2,	5,	9,	37,		2));
		getPins().add(createAnalogInputPin("P9_38",	"AIN3",		2,	6,	9,	38,		3));
		getPins().add(createAnalogInputPin("P9_39",	"AIN0",		2,	3,	9,	39,		0));
		getPins().add(createAnalogInputPin("P9_40",	"AIN1",		2,	4,	9,	40,		1));
		
		getPins().add(createDigitalIOPin("P9_41",	"GPIO0_20",	0,	20,	9,	31));
		getPins().add(createDigitalIOPin("P9_42",	"GPIO0_7",	0,	 7,	9,	31));
		
		
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
