package org.bulldog.beagleboneblack.jni;

public class NativePwm {

	public static native int setup();
	public static native void teardown();
	public static native int setPwm(int pwmId, float frequency, float dutyA, float dutyB);
	public static native void enable(int pwmId);
	public static native void disable(int pwmId);
	
	private static boolean isInitialized = false;

	static {
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
}
