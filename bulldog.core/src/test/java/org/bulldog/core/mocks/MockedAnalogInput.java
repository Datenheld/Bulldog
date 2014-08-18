package org.bulldog.core.mocks;

import java.util.concurrent.Future;

import org.bulldog.core.gpio.Pin;
import org.bulldog.core.gpio.base.AbstractAnalogInput;

public class MockedAnalogInput extends AbstractAnalogInput {

	private double value;
	private double[] samples;
	
	public MockedAnalogInput(Pin pin) {
		super(pin);
	}

	public void setValueToRead(double value) {
		this.value = value;
	}
	
	public void setSamples(double[] samples) {
		this.samples = samples;
	}
	
	@Override
	public double read() {
		return value;
	}

	@Override
	public double[] sample(int amountSamples) {
		return samples;
	}

	@Override
	public double[] sample(int amountSamples, float frequency) {
		return samples;
	}

	@Override
	public Future<double[]> sampleAsync(int amountSamples) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Future<double[]> sampleAsync(int amountSamples, float frequency) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void setupImpl() {
	}

	@Override
	protected void teardownImpl() {
	}

}
