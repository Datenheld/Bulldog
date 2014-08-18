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
	protected void setupImpl() {
		RaspberryPiPin pin = (RaspberryPiPin)getPin();
		BCM2835.configureAlternateFunction(pin.getGpioNumber(), 5);
		stopClock();
		int value = BCM2835.getPwmMemory().getIntValueAt(BCM2835.PWM_CTL);
		value = BitMagic.setBit(value, 5, 0);
		value = BitMagic.setBit(value, 7, 1);
		BCM2835.getPwmMemory().setIntValue(BCM2835.PWM_CTL, value);
		value = BCM2835.getPwmMemory().getIntValueAt(BCM2835.PWM_CTL);
	}

	@Override
	protected void teardownImpl() {
		disableImpl();
		stopClock();
	}

	@Override
	protected void setPwmImpl(double frequency, double duty) {
		if(previousFrequency != frequency) {
			setFrequencyImpl(frequency);
			previousFrequency = frequency;
		} 
		
		setDutyImpl(duty);
	}

	private void setFrequencyImpl(double frequency) {
		if(isEnabled()) { disableImpl(); }
		
		int divisorRegister = PwmFrequencyCalculator.calculateDivisorRegister(frequency);
		BCM2835.getClockMemory().setIntValue(BCM2835.PWMCLK_DIV, divisorRegister);
		BCM2835.getPwmMemory().setIntValue(BCM2835.PWM_RNG1, 0x100000);
		BulldogUtil.sleepMs(1);
		startClock();
		
		if(isEnabled()) { enableImpl(); }
	}
	
	protected void setDutyImpl(double duty) {
		int myDuty = (int)(0x100000 * duty);
		BCM2835.getPwmMemory().setIntValue(BCM2835.PWM_DAT1, myDuty);
	}
	
	private void startClock() {
		BCM2835.getClockMemory().setIntValue(BCM2835.PWMCLK_CNTL, 0x5A000011);
	}
	
	private void stopClock() {
		BCM2835.getClockMemory().setIntValue(BCM2835.PWMCLK_CNTL,  0x5A000000 | (1 << 5));
		BulldogUtil.sleepMs(1);
	}

	@Override
	protected void enableImpl() {
		int value = BCM2835.getPwmMemory().getIntValueAt(BCM2835.PWM_CTL);
		BCM2835.getPwmMemory().setIntValue(BCM2835.PWM_CTL,  BitMagic.setBit(value, 0, 1));
		BulldogUtil.sleepMs(1);
	}

	@Override
	protected void disableImpl() {
		int value = BCM2835.getPwmMemory().getIntValueAt(BCM2835.PWM_CTL);
		BCM2835.getPwmMemory().setIntValue(BCM2835.PWM_CTL, BitMagic.setBit(value, 0, 0));
		BulldogUtil.sleepMs(1);
	}

}
