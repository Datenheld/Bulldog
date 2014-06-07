package org.bulldog.devices.pwmdriver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bulldog.core.gpio.Pin;
import org.bulldog.core.gpio.Pwm;
import org.bulldog.core.io.bus.BusConnection;
import org.bulldog.core.io.bus.i2c.I2cBus;
import org.bulldog.core.io.bus.i2c.I2cDevice;
import org.bulldog.core.util.BulldogUtil;

public class PCA9685 extends I2cDevice {
	
	private static final String ERROR_INVALID_CHANNEL = "Invalid Channel %d - There are 16 channels on this driver: 0 to 15.";
	private static final String NAME = "PCA9685 PWM Driver";
	
	private static final int PCA9685_MODE1 = 0x00;
	private static final int PCA9685_PRESCALE = 0xFE;
	
	private static final int PWM_BASE_ADDRESS = 0x06;

	private List<Pwm> channels = new ArrayList<Pwm>();
	private float currentFrequency = 0.0f;
	
	private boolean isSetup = false;
	
	public PCA9685(BusConnection connection) {
		super(connection);
		setName(NAME);
		setupPwmChannels();
	}

	public PCA9685(I2cBus bus, int address) {
		this(bus.createConnection(address));
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

	public void setFrequency(float frequency) {		
		if(frequency == currentFrequency) { return; }
		try {
			
			setupIfNecessary(); 
			
			byte prescaler = (byte)Math.round(25000000 / (4096 * frequency) - 1);
			byte oldmode = readFromRegister(PCA9685_MODE1);
			byte newmode = (byte) ((oldmode & 0x7F) | 0x10); 	// sleep
			writeToRegister(PCA9685_MODE1, newmode); 			// go to sleep
			writeToRegister(PCA9685_PRESCALE, prescaler); 		// set the prescaler
			writeToRegister(PCA9685_MODE1, oldmode);
			BulldogUtil.sleepMs(5);
			writeToRegister(PCA9685_MODE1, (byte) (oldmode | 0xa1)); 
		} catch(Exception ex) {
			throw new RuntimeException(ex);
		}
		
		this.currentFrequency = frequency;
	}

	private void setupIfNecessary() throws IOException {
		if(isSetup == false) {
			writeToRegister(PCA9685_MODE1, (byte)0x0F);
			isSetup = true;
		}
	}

	public void setDuty(int channel, float duty) {
		try {
		  int offValue = (int)(4096 * duty);
		  int register = PWM_BASE_ADDRESS+4*channel;
		  writeToRegister(register, (byte)0);
		  writeToRegister(register + 1, (byte)0);
		  writeToRegister(register + 2, (byte)offValue);
		  writeToRegister(register + 3, (byte)(offValue >> 8));
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
