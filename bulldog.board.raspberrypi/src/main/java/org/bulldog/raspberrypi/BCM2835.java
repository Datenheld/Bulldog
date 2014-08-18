package org.bulldog.raspberrypi;

import org.bulldog.linux.io.mmap.MemoryMap;

public class BCM2835 {

	public static final int BCM2708_PERI_BASE = 0x20000000;
	public static final int GPIO_BASE         = (BCM2708_PERI_BASE + 0x200000);
	public static final int PWM_BASE		  = (BCM2708_PERI_BASE + 0x20C000);
	public static final int CLOCK_BASE		  = (BCM2708_PERI_BASE + 0x101000);
	
	public static final int PWMCLK_CNTL 	  =	40 * 4;
	public static final int PWMCLK_DIV  	  =	41 * 4;
	
	public static final int PWM_CTL = 0;
	public static final int PWM_RNG1 = 4 * 4;
	public static final int	PWM_DAT1 = 5 * 4;

	public static final int GPIO_SET = 7 * 4;
	public static final int GPIO_CLEAR = 10 * 4;
	
	private static MemoryMap gpioMemory;
	private static MemoryMap pwmMemory;
	private static MemoryMap clockMemory;
	
	public static MemoryMap getGpioMemory() {
		if(gpioMemory == null) {
			gpioMemory = new MemoryMap("/dev/mem", BCM2835.GPIO_BASE, 4096, 0);
		}
		
		return gpioMemory;
	}
	
	public static MemoryMap getPwmMemory() {
		if(pwmMemory == null) {
			pwmMemory = new MemoryMap("/dev/mem", BCM2835.PWM_BASE, 4096, 0);
		}
		
		return pwmMemory;
	}
	
	public static MemoryMap getClockMemory() {
		if(clockMemory == null) {
			clockMemory = new MemoryMap("/dev/mem", BCM2835.CLOCK_BASE, 4096, 0);
		}
		
		return clockMemory;
	}
	
	public static void cleanup() {
		if(gpioMemory != null) { gpioMemory.closeMap(); }
		if(pwmMemory != null)  { pwmMemory.closeMap(); }
		if(clockMemory != null) { clockMemory.closeMap(); }
	}
	
	public static void configureAsInput(int gpio) {
		long address = (gpio / 10) * 4;
		int value = BCM2835.getGpioMemory().getIntValueAt(address);
		value &= ~(7 << getGpioRegisterOffset(gpio));
		BCM2835.getGpioMemory().setIntValue(address, value);
	}
	
	public static void configureAsOutput(int gpio) {
		long address = (gpio / 10) * 4;
		int value = BCM2835.getGpioMemory().getIntValueAt(address);
		value |=  (1 << getGpioRegisterOffset(gpio));
		BCM2835.getGpioMemory().setIntValue(address, value);
	}
	
	public static void configureAlternateFunction(int gpio, int alt) {
		long address = (gpio / 10) * 4;
		int value = BCM2835.getGpioMemory().getIntValueAt(address);
		value |= (( (alt) <=3 ? (alt) + 4 : (alt) == 4 ? 3 : 2 ) << (gpio % 10) * 3);
		BCM2835.getGpioMemory().setIntValue(address, value);
	}
	
	public static int getGpioRegisterOffset(int gpio) {
		return (gpio % 10) * 3;
	}
}
