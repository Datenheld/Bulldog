package org.bulldog.beagleboneblack.gpio;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;

import org.bulldog.beagleboneblack.jni.NativeAdc;
import org.bulldog.core.gpio.Pin;
import org.bulldog.core.gpio.base.AbstractAnalogInput;

public class BBBAnalogInput extends AbstractAnalogInput {

	private IntBuffer buffer;
	private int channelId = -1;
	
	public BBBAnalogInput(Pin pin, int pwmChannel) {
		super(pin);
	}

	public void setup() {
		if(buffer == null) {
			buffer = ByteBuffer.allocateDirect(4 * 100).order(ByteOrder.LITTLE_ENDIAN).asIntBuffer();
		}
		
		NativeAdc.configureModule(NativeAdc.BBBIO_ADC_WORK_MODE_BUSY_POLLING, 1);
		NativeAdc.configureChannel(channelId, NativeAdc.BBBIO_ADC_STEP_MODE_SW_CONTINUOUS, 0, 1, 1, buffer, 100);
		NativeAdc.enableChannel(channelId);
	}

	public void teardown() {
		NativeAdc.disableChannel(channelId);
	}

	@Override
	public double readValue() {
		return sample(1)[0];
	}

	@Override
	public double[] sample(int amountSamples) {
		if(buffer.limit() < amountSamples) {
			buffer = ByteBuffer.allocateDirect(4 * amountSamples).order(ByteOrder.LITTLE_ENDIAN).asIntBuffer();
			NativeAdc.configureChannel(channelId, NativeAdc.BBBIO_ADC_STEP_MODE_SW_CONTINUOUS, 0, 1, 1, buffer, amountSamples);
		}
		
		NativeAdc.fetchSamples(amountSamples);
		double[] doubles = new double[amountSamples];
		buffer.rewind();
	    for(int i = 0; i < amountSamples; i++) {
	    	int bufferGet = buffer.get();
	    	doubles[i] = (bufferGet) / 4095.0;
	    } 
	   
	    return doubles;
	}

	@Override
	public double[] sample(double frequency, int amountSamples) {
		throw new UnsupportedOperationException();
	}

}
