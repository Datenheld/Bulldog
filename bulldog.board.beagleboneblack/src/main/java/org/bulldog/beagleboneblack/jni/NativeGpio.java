package org.bulldog.beagleboneblack.jni;

public class NativeGpio {

	public static final int DIRECTION_OUT = 1;
	public static final int DIRECTION_IN = 0;

	public static final int HIGH = 1;
	public static final int LOW = 0;

	public static native boolean setup();
	public static native boolean teardown();
	public static native void pinMode(int port, int pin, int direction);
	public static native int digitalRead(int port, int pin);
	public static native void digitalWrite(int port, int pin, int state);
	public static native void enableGpio();
	//public static native int debouncePin(int port, int pin, int ms);
	
	private static boolean isInitialized = false;

	static {
		initialize();
	}

	public static int getBank(int pin) {
		return (int) (pin / 32);
	}

	public static void initialize() {
		if (isInitialized) {
			return;
		}
		setup();
		enableGpio();
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
}
