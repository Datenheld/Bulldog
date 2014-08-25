package org.bulldog.beagleboneblack.jni;

import java.nio.IntBuffer;


public class NativeAdc {

	public static final int BBBIO_ADC_STEP_MODE_SW_ONE_SHOOT 	=	0x0;
	public static final int BBBIO_ADC_STEP_MODE_SW_CONTINUOUS	=	0x1;
	public static final int BBBIO_ADC_STEP_MODE_HW_ONE_SHOOT 	=	0x2;
	public static final int BBBIO_ADC_STEP_MODE_HW_CONTINUOUS 	=	0x3;
	
	public static final int BBBIO_ADC_STEP_AVG_1 	=	0x0;
	public static final int BBBIO_ADC_STEP_AVG_2 	=	0x1;
	public static final int BBBIO_ADC_STEP_AVG_4 	=	0x2;
	public static final int BBBIO_ADC_STEP_AVG_8 	=	0x3;
	public static final int BBBIO_ADC_STEP_AVG_16 	=	0x4;
	
	public static final int BBBIO_ADC_WORK_MODE_BUSY_POLLING =	0x1;
	public static final int BBBIO_ADC_WORK_MODE_TIMER_INT 	 =	0x2;
	
	public static final float SYSCLOCK_HZ = 24000000 / 14;
	
	public static native int setup();
	public static native void teardown();
	public static native void fetchSamples(int amountSamples);
	public static native void configureModule(int workType, int clockDivisor);
	public static native void configureChannel(int channel, int mode, int startDelay, int sampleDelay, int sampleSize, IntBuffer buffer, int bufferSize);
	public static native void enableChannel(int channel);
	public static native void disableChannel(int channel);
	
	private static boolean isInitialized = false;

	static {
		NativeGpio.initialize();
		initialize();
	}
	
	public static void initialize() {
		if (isInitialized) {
			return;
		}
		setup();
		isInitialized = true;
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				deinitialize();
			}
		});
	}

	public static void deinitialize() {
		if (isInitialized) {
			teardown();
			isInitialized = false;
		}
	}
	
	public static int calculateNearestDivisorForFrequency(float desiredFrequency) {
		float divisor = SYSCLOCK_HZ / desiredFrequency;
		return Math.round(divisor);
	}
	
}
