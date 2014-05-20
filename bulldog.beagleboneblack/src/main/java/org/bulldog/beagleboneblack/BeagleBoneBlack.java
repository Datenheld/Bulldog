package org.bulldog.beagleboneblack;

import java.io.File;
import java.security.CodeSource;

import org.bulldog.beagleboneblack.gpio.BBBAnalogInput;
import org.bulldog.beagleboneblack.gpio.BBBDigitalInput;
import org.bulldog.beagleboneblack.gpio.BBBDigitalOutput;
import org.bulldog.beagleboneblack.gpio.BBBPwm;
import org.bulldog.beagleboneblack.io.BBBI2cBus;
import org.bulldog.beagleboneblack.io.BBBSerialPort;
import org.bulldog.beagleboneblack.sysfs.SysFs;
import org.bulldog.core.gpio.Pin;
import org.bulldog.core.gpio.PinFeature;
import org.bulldog.core.gpio.base.AbstractPwm;
import org.bulldog.core.gpio.event.ActivationEventArgs;
import org.bulldog.core.gpio.event.ActivationListener;
import org.bulldog.core.io.SerialPort;
import org.bulldog.core.io.bus.I2cBus;
import org.bulldog.core.platform.AbstractBoard;
import org.bulldog.core.platform.Board;

public class BeagleBoneBlack extends AbstractBoard implements ActivationListener {
	
	private static BeagleBoneBlack instance;
	private static final String NAME = "BeagleBone Black";
	private SysFs sysFs = new SysFs();
		
	private BeagleBoneBlack() {
		createPins();
		createBuses();
		createShutdownHook();
	}
	
	private void createPins() {
		getPins().add(createDigitalIOPin("P8_3", 	"GPIO1_6",	1,  6, 	"P8", 	 3));
		getPins().add(createDigitalIOPin("P8_3", 	"GPIO1_6",	1,  6, 	"P8", 	 3));
		getPins().add(createDigitalIOPin("P8_4", 	"GPIO1_7", 	1,  7,  "P8", 	 4));
		getPins().add(createDigitalIOPin("P8_5", 	"GPIO1_2", 	1,  2,  "P8", 	 5));
		getPins().add(createDigitalIOPin("P8_6", 	"GPIO1_3", 	1,  3,  "P8",	 6));
		getPins().add(createDigitalIOPin("P8_7", 	"TIMER4",  	2,  2,	"P8",	 7));
		getPins().add(createDigitalIOPin("P8_8", 	"TIMER7",  	2,  3, 	"P8",	 8));
		getPins().add(createDigitalIOPin("P8_9", 	"TIMER5",  	2,  5,  "P8",	 9));
		getPins().add(createDigitalIOPin("P8_10", 	"TIMER6",  	2,  4, 	"P8",	10));
		getPins().add(createDigitalIOPin("P8_11", 	"GPIO1_13", 1, 13,	"P8",	11));
		getPins().add(createDigitalIOPin("P8_12", 	"GPIO1_12", 1, 12,	"P8",	12));
		getPins().add(createDigitalIOPin("P8_13", 	"EHRPWM2B", 0, 23,	"P8",	13));
		getPins().add(createDigitalIOPin("P8_14", 	"GPIO0_26", 0, 26,	"P8",	14));
		getPins().add(createDigitalIOPin("P8_15", 	"GPIO1_15", 1, 15,	"P8",	15));
		getPins().add(createDigitalIOPin("P8_16", 	"GPIO1_14", 1, 14,	"P8",	16));
		getPins().add(createDigitalIOPin("P8_17", 	"GPIO0_27", 0, 27,	"P8",	17));
		getPins().add(createDigitalIOPin("P8_18", 	"GPIO2_1", 	2,  1,	"P8",	18));
		getPins().add(createDigitalIOPin("P8_19", 	"EHRPWM2A", 0, 22,	"P8",	19));
		getPins().add(createDigitalIOPin("P8_20", 	"GPIO1_31", 1, 31,	"P8",	20));
		getPins().add(createDigitalIOPin("P8_21", 	"GPIO1_30",	1, 30,  "P8", 	21));
		getPins().add(createDigitalIOPin("P8_22", 	"GPIO1_5", 	1,  5,  "P8", 	22));
		getPins().add(createDigitalIOPin("P8_23", 	"GPIO1_4",	1,  4, 	"P8", 	23));
		getPins().add(createDigitalIOPin("P8_24", 	"GPIO1_1", 	1,  1,  "P8", 	24));
		getPins().add(createDigitalIOPin("P8_25", 	"GPIO1_0",	1,  0, 	"P8", 	25));
		getPins().add(createDigitalIOPin("P8_26", 	"GPIO1_29", 1,  29, "P8", 	26));
		getPins().add(createDigitalIOPin("P8_27", 	"GPIO2_22",	2,  22, "P8", 	27));
		getPins().add(createDigitalIOPin("P8_28", 	"GPIO2_24", 2,  24, "P8", 	28));
		getPins().add(createDigitalIOPin("P8_29", 	"GPIO2_23",	2,  23, "P8", 	29));
		getPins().add(createDigitalIOPin("P8_30", 	"GPIO2_25", 2,  25, "P8", 	30));
		getPins().add(createDigitalIOPin("P8_31", 	"GPIO0_10",	0,  10,	"P8", 	31));
		getPins().add(createDigitalIOPin("P8_32", 	"GPIO0_11",	0,  11, "P8",	32));
		getPins().add(createDigitalIOPin("P8_33", 	"GPIO0_9",	0,   9,	"P8", 	33));
		getPins().add(createDigitalIOPin("P8_34", 	"GPIO2_17",	2,  17, "P8", 	34));
		getPins().add(createDigitalIOPin("P8_35", 	"GPIO0_8",	0,   8,	"P8", 	35));
		getPins().add(createDigitalIOPin("P8_36", 	"GPIO2_16",	2,  16, "P8", 	36));
		getPins().add(createDigitalIOPin("P8_37", 	"GPIO2_14",	2,  14,	"P8", 	37));
		getPins().add(createDigitalIOPin("P8_38", 	"GPIO2_1", 	2,   1, "P8", 	38));
		getPins().add(createDigitalIOPin("P8_39", 	"GPIO2_12",	2,  12,	"P8", 	39));
		getPins().add(createDigitalIOPin("P8_40", 	"GPIO2_13",	2,  14, "P8", 	40));
		getPins().add(createDigitalIOPin("P8_41", 	"GPIO2_10",	2,  10,	"P8", 	41));
		getPins().add(createDigitalIOPin("P8_42", 	"GPIO2_11",	2,  11, "P8", 	42));
		getPins().add(createDigitalIOPin("P8_43", 	"GPIO2_8",	2,   8,	"P8", 	43));
		getPins().add(createDigitalIOPin("P8_44", 	"GPIO2_9", 	2,   9, "P8", 	44));
		getPins().add(createDigitalIOPin("P8_45", 	"GPIO2_6",	2,   6,	"P8", 	45));
		getPins().add(createDigitalIOPin("P8_46", 	"GPIO2_27",	2,  27, "P8", 	46));
		
		getPins().add(createDigitalIOPin("P9_11", 	"UART4_RX", 0, 30,	"P9",	11));
		getPins().add(createDigitalIOPin("P9_12", 	"GPIO1_28", 1, 28,	"P9",	12));
		getPins().add(createDigitalIOPin("P9_13", 	"UART4_TX", 0, 31,	"P9",	13));
		getPins().add(createDigitalIOPin("P9_14", 	"EHRPWM1A", 1, 18,	"P9",	14));
		getPins().add(createDigitalIOPin("P9_15", 	"GPIO1_16", 1, 16,	"P9",	15));
		getPins().add(createDigitalIOPin("P9_16", 	"EHRPWM1B", 1, 19,	"P9",	16));
		getPins().add(createDigitalIOPin("P9_17", 	"GPIO0_5",  0,  5,	"P9",	17));
		getPins().add(createDigitalIOPin("P9_18", 	"GPIO0_4", 	0,  4,	"P9",	18));
		getPins().add(createDigitalIOPin("P9_19", 	"GPIO0_15", 0, 15,	"P9",	19));
		getPins().add(createDigitalIOPin("P9_20", 	"GPIO0_14", 0, 14,	"P9",	20));
		getPins().add(createDigitalIOPin("P9_21", 	"UART2TX",	0,  3,  "P9", 	21));
		getPins().add(createDigitalIOPin("P9_22", 	"UART2RX", 	0,  2,  "P9", 	22));
		getPins().add(createDigitalIOPin("P9_23", 	"GPIO1_17",	1,  17,	"P9", 	23));
		getPins().add(createDigitalIOPin("P9_24", 	"UART1TX", 	0,  12, "P9", 	24));
		getPins().add(createDigitalIOPin("P9_25", 	"GPIO3_21",	3,  21, "P9", 	25));
		getPins().add(createDigitalIOPin("P9_26", 	"UART1RX",  0,  13, "P9", 	26));
		getPins().add(createDigitalIOPin("P9_27", 	"GPIO3_19",	3,  19, "P9", 	27));
		getPins().add(createDigitalIOPin("P9_28", 	"GPIO3_17", 3,  17, "P9", 	28));
		getPins().add(createDigitalIOPin("P9_29",	"GPIO3_15",	3,	15,	"P9",	29));
		getPins().add(createDigitalIOPin("P9_30",	"GPIO3_16",	3,	16,	"P9",	30));
		getPins().add(createDigitalIOPin("P9_31",	"EHRPWM0A",	3,	14,	"P9",	31));
		
		getPins().add(createAnalogInputPin("P9_33",	"AIN4",		2,	7,	"P9",	33, 	4));
		getPins().add(createAnalogInputPin("P9_35",	"AIN6",		2,	9,	"P9",	35,		6));
		getPins().add(createAnalogInputPin("P9_36",	"AIN5",		2,	8,	"P9",	36,		5));
		getPins().add(createAnalogInputPin("P9_37",	"AIN2",		2,	5,	"P9",	37,		2));
		getPins().add(createAnalogInputPin("P9_38",	"AIN3",		2,	6,	"P9",	38,		3));
		getPins().add(createAnalogInputPin("P9_39",	"AIN0",		2,	3,	"P9",	39,		0));
		getPins().add(createAnalogInputPin("P9_40",	"AIN1",		2,	4,	"P9",	40,		1));
		
		getPins().add(createDigitalIOPin("P9_41",	"GPIO0_20",	0,	20,	"P9",	31));
		getPins().add(createDigitalIOPin("P9_42",	"GPIO0_7",	0,	 7,	"P9",	31));
		
		
		addPwmToPin(getPinByName("P8_13"));
		addPwmToPin(getPinByName("P8_19"));
		addPwmToPin(getPinByName("P8_45"));
		addPwmToPin(getPinByName("P8_46"));
		addPwmToPin(getPinByName("P9_14"));
		addPwmToPin(getPinByName("P9_16"));
		addPwmToPin(getPinByName("P9_21"));
		addPwmToPin(getPinByName("P9_22"));
		addPwmToPin(getPinByName("P9_29"));
		addPwmToPin(getPinByName("P9_31"));
	}

	private Pin createDigitalIOPin(String name, String internalName, int bank, int pinIndex, String port, int portIndex) {
		BeagleBonePin pin = new BeagleBonePin(name, internalName, bank, pinIndex, port, portIndex);
		pin.addFeature(new BBBDigitalOutput(pin))
		   .addFeature(new BBBDigitalInput(pin));
		return pin;
	}
	
	private void addPwmToPin(Pin pin) {
		pin.addFeature(new BBBPwm(pin));
		pin.addActivationListener(this);
	}
	
	private Pin createAnalogInputPin(String name, String internalName, int bank, int pinIndex, String port, int portIndex, int channelId) {
		BeagleBonePin pin = new BeagleBonePin(name, internalName, bank, pinIndex, port, portIndex);
		pin.addFeature(new BBBAnalogInput(pin, channelId));
		return pin;
	}
	

	private void createBuses() {
		detectI2cBuses();
		detectSerialPorts();
	}

	private void detectI2cBuses() {
		for(int i = 0; i < 3; i++) {
			File i2cDevice = new File("/dev/i2c-" + i);
			if(i2cDevice.exists()) {
				I2cBus bus = new BBBI2cBus(i2cDevice.getAbsolutePath());
				if(!getI2cBuses().contains(bus)) {
					getI2cBuses().add(bus);
				}
			}
		}
	}

	private void detectSerialPorts() {
		for(int i = 0; i < 6; i++) {
			File serialDevice = new File("/dev/ttyO" + i);
			if(serialDevice.exists()) {
				SerialPort port = new BBBSerialPort(serialDevice.getAbsolutePath());
				if(!getSerialPorts().contains(port)) {
					getSerialPorts().add(port);
				}
			}
		}
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

	public synchronized static Board getInstance() {
		if(instance == null) {
			instance = createBoardImpl();
		}
		return instance;
	}
	
	private static BeagleBoneBlack createBoardImpl() {
		String codeDirectory =  getJarFileDirectory();
		File file = new File(codeDirectory + "/libbulldog-beagleboneblack.so");
		if(file.exists()) {
			System.load(file.getAbsolutePath());
		}  else {
			System.loadLibrary("bulldog-beagleboneblack");
		}
		
		return new BeagleBoneBlack();
	}

	private static String getJarFileDirectory() {
		try {
			CodeSource codeSource = BeagleBoneBlack.class.getProtectionDomain().getCodeSource();
			File jarFile = new File(codeSource.getLocation().toURI().getPath());
			String jarDir = jarFile.getParentFile().getPath();
			return jarDir;
		} catch(Exception ex) {
			return null;
		}
	}
	
	public void loadSlot(String deviceId) {
		sysFs.createSlotIfNotExists(deviceId);
	}
	
	public void removeSlot(String deviceId) {
		sysFs.removeSlotOfDevice(deviceId);
	}
}
