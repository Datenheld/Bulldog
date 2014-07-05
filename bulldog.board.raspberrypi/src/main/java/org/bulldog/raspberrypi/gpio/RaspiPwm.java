package org.bulldog.raspberrypi.gpio;

import org.bulldog.core.gpio.Pin;
import org.bulldog.core.gpio.base.AbstractPwm;
import org.bulldog.core.util.BitMagic;
import org.bulldog.core.util.BulldogUtil;
import org.bulldog.raspberrypi.BCM2835;
import org.bulldog.raspberrypi.RaspberryPiPin;

public class RaspiPwm extends AbstractPwm {

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
		value = BitMagic.setBit(value, 5, 1);
		value = BitMagic.setBit(value, 7, 1);
		BCM2835.getPwmMemory().setValue(BCM2835.PWM_CTL, value);
	}

	@Override
	public void teardown() {

	}

	@Override
	protected void setPwmImpl(double frequency, double duty) {
		int divisorRegister = PwmFrequencyCalculator.calculateDivisorRegister(frequency);
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
