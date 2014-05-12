package org.bulldog.core.gpio;

import org.bulldog.core.gpio.event.ThresholdListener;

public interface AnalogInput extends PinFeature {

	double readValue();
	double[] sample(int amountSamples);
	double[] sample(double frequency, int amountSamples);
	void startMonitor(int periodMicroSeconds, ThresholdListener listener);
	void stopMonitor();

}