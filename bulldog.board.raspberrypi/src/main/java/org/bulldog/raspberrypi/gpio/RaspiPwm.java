package org.bulldog.raspberrypi.gpio;

import org.bulldog.core.gpio.Pin;
import org.bulldog.core.gpio.base.AbstractPwm;
import org.bulldog.core.util.BitMagic;
import org.bulldog.core.util.BulldogUtil;
import org.bulldog.raspberrypi.BCM2835;
import org.bulldog.raspberrypi.RaspberryPiPin;

public class RaspiPwm extends AbstractPwm {

	private double previousFrequency = 0.0;
	
	public RaspiPwm(Pin pin) {
		super(pin);
	}

	@Override
	public void setup() {
		RaspberryPiPin pin = (RaspberryPiPin)getPin();
		BCM2835.configureAlternateFunction(pin.getGpioAddress(), 5);
		BCM2835.getClockMemory().setValue(BCM2835.PWMCLK_CNTL,  0x5A000000 | (1 << 5));
		BulldogUtil.sleepMs(1);
		int value = BCM2835.getPwmMemory().getValueAt(BCM2835.PWM_CTL);
		value = BitMagic.setBit(value, 5, 0);
		value = BitMagic.setBit(value, 7, 1);
		BCM2835.getPwmMemory().setValue(BCM2835.PWM_CTL, value);
		value = BCM2835.getPwmMemory().getValueAt(BCM2835.PWM_CTL);
	}

	@Override
	public void teardown() {

	}

	@Override
	protected void setPwmImpl(double frequency, double duty) {
		if(previousFrequency != frequency) {
			if(isEnabled()) { disableImpl(); }
			
			int divisorRegister = PwmFrequencyCalculator.calculateDivisorRegister(frequency);
			BCM2835.getClockMemory().setValue(BCM2835.PWMCLK_DIV, divisorRegister);
			BCM2835.getPwmMemory().setValue(BCM2835.PWM_RNG1, 0x400);
			setDutyImpl(duty);
			BulldogUtil.sleepMs(1);
			BCM2835.getClockMemory().setValue(BCM2835.PWMCLK_CNTL, 0x5A000011);
			previousFrequency = frequency;
			
			if(isEnabled()) { enableImpl(); }
		} else {
			setDutyImpl(duty);
		}
	}
	
	protected void setDutyImpl(double duty) {
		int myDuty = (int)(0x400 * duty);
		BCM2835.getPwmMemory().setValue(BCM2835.PWM_DAT1, myDuty);
	}

	@Override
	protected void enableImpl() {
		int value = BCM2835.getPwmMemory().getValueAt(BCM2835.PWM_CTL);
		BCM2835.getPwmMemory().setValue(BCM2835.PWM_CTL,  BitMagic.setBit(value, 0, 1));
		BulldogUtil.sleepMs(1);
	}

	@Override
	protected void disableImpl() {
		int value = BCM2835.getPwmMemory().getValueAt(BCM2835.PWM_CTL);
		BCM2835.getPwmMemory().setValue(BCM2835.PWM_CTL, BitMagic.setBit(value, 0, 0));
		BulldogUtil.sleepMs(1);
	}

}
