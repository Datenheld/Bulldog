package org.bulldog.devices.pwmdriver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bulldog.core.gpio.Pin;
import org.bulldog.core.gpio.Pwm;
import org.bulldog.core.io.bus.i2c.I2cBus;
import org.bulldog.core.io.bus.i2c.I2cConnection;
import org.bulldog.core.io.bus.i2c.I2cDevice;
import org.bulldog.core.util.BulldogUtil;

public class PCA9685 extends I2cDevice {
	
	private static final String ERROR_INVALID_CHANNEL = "Invalid Channel %d - There are 16 channels on this driver: 0 to 15.";
	private static final String NAME = "PCA9685 PWM Driver";
	
	private static final int PCA9685_MODE1 = 0x00;
	private static final int PCA9685_PRESCALE = 0xFE;
	
	private static final int PWM_BASE_ADDRESS = 0x06;

	private List<Pwm> channels = new ArrayList<Pwm>();
	private double currentFrequency = 0.0;
	
	private boolean isSetup = false;
	
	private FrequencyLookupTable lookupFrequency = new FrequencyLookupTable();
	
	public PCA9685(I2cConnection connection) {
		super(connection);
		setName(NAME);
		setupPwmChannels();
	}

	public PCA9685(I2cBus bus, int address) {
		this(bus.createI2cConnection(address));
	}

	private void setupPwmChannels() {
		for (int i = 0; i < 16; i++) {
			Pin pin = new Pin("P" + i, i, "P", i);
			PCA9685Pwm pwm = new PCA9685Pwm(pin, this);
			pin.getFeatures().add(pwm);
			pin.activateFeature(Pwm.class);
			channels.add(pwm);
		}
	}

	public void setFrequency(double frequency) {		
		if(frequency == currentFrequency) { return; }
		try {
			
			setupIfNecessary(); 
			
			int prescale = 0;
			if(lookupFrequency.containsKey(frequency)) {
				prescale = lookupFrequency.get(frequency);
			} else {
				prescale = (int)Math.round(25000000.0f / (4096.0f * frequency) - 1.0);
			}
			
			int oldmode = readByteFromRegister(PCA9685_MODE1);
			int newmode = ((oldmode & 0x7F) | 0x10); 		// sleep
			writeByteToRegister(PCA9685_MODE1, newmode); 		// go to sleep
			writeByteToRegister(PCA9685_PRESCALE, prescale); 	// set the prescaler
			writeByteToRegister(PCA9685_MODE1, oldmode);
			BulldogUtil.sleepMs(5);
			writeByteToRegister(PCA9685_MODE1, (oldmode | 0xa1)); 
		} catch(Exception ex) {
			throw new RuntimeException(ex);
		}
		
		this.currentFrequency = frequency;
	}

	private void setupIfNecessary() throws IOException {
		if(isSetup == false) {
			writeByteToRegister(PCA9685_MODE1, 0x0F);
			isSetup = true;
		}
	}

	public void setDuty(int channel, double duty) {
		try {
		  int offValue = (int)(4096 * duty);
		  int register = PWM_BASE_ADDRESS+4*channel;
		  writeByteToRegister(register, 0);
		  writeByteToRegister(register + 1, 0);
		  writeByteToRegister(register + 2, offValue);
		  writeByteToRegister(register + 3, (offValue >> 8));
		} catch(Exception ex) {
			throw new RuntimeException(ex);
		}
		
	}

	public void enableChannel(int channel) {
		setDuty(channel, getChannel(channel).getDuty());
	}

	public void disableChannel(int channel) {
		setDuty(channel, 0.0f);
	}

	public Pwm getChannel(int channel) {
		if (channel < 0 || channel > 15) {
			throw new IllegalArgumentException(String.format(ERROR_INVALID_CHANNEL, channel));
		}

		for (Pwm pwm : getChannels()) {
			if (pwm.getPin().getAddress() == channel) {
				return pwm;
			}
		}

		return null;
	}

	public List<Pwm> getChannels() {
		return Collections.unmodifiableList(channels);
	}

}
