package org.bulldog.devices.motor;

import org.bulldog.core.gpio.DigitalOutput;

import org.bulldog.core.io.PinIOGroup;

public class UnipolarStepper extends AbstractStepper {

	private byte[] steps = new byte[] { 0b0101, 0b0110, 0b1010, 0b1001 };
	private double position;
	
	public UnipolarStepper(DigitalOutput out1, DigitalOutput out2, DigitalOutput out3, DigitalOutput out4) {
		
	}
	
	public UnipolarStepper(PinIOGroup group) {
		
	}
	
	@Override
	public void moveTo(double position) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setPosition(double position) {
		// TODO Auto-generated method stub
		
	}
	
	public void step() {
	}

	@Override
	public double getPosition() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getMillisecondsPerUnit() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getRefreshIntervalMilliseconds() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void forward() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void backward() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isMoving() {
		// TODO Auto-generated method stub
		return false;
	}

}
