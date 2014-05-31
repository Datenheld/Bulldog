package org.bulldog.core.gpio;

import java.util.concurrent.Future;

import org.bulldog.core.gpio.event.ThresholdListener;

public interface AnalogInput extends PinFeature {

	double read();
	double[] sample(int amountSamples);
	double[] sample(int amountSamples, float frequency);
	Future<double[]> sampleAsync(int amountSamples);
	Future<double[]> sampleAsync(int amountSamples, float frequency);
	void startMonitor(int periodMicroSeconds, ThresholdListener listener);
	void stopMonitor();

}