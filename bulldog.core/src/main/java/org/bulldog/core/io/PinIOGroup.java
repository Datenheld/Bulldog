package org.bulldog.core.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.bulldog.core.Signal;
import org.bulldog.core.gpio.DigitalOutput;
import org.bulldog.core.gpio.Pin;
import org.bulldog.core.util.BulldogUtil;

public class PinIOGroup implements IOPort {
	
	private Pin[] dataPins;
	private Pin enablePin;
	private PinIOInputStream inputStream;
	private PinIOOutputStream outputStream;
	
	private String name;
	private String alias;
	
	public PinIOGroup(Pin enablePin, Pin... dataPins) {
		this.enablePin = enablePin;
		this.dataPins = dataPins;
		inputStream = new PinIOInputStream(this);
		outputStream = new PinIOOutputStream(this);
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getAlias() {
		return alias;
	}

	@Override
	public void setAlias(String alias) {
		this.alias = alias;
	}

	@Override
	public void open() throws IOException {
		
	}

	@Override
	public boolean isOpen() {
		return true;
	}

	@Override
	public void close() throws IOException {
	}

	@Override
	public void writeByte(int b) throws IOException {
		getOutputStream().write(b);
	}

	@Override
	public void writeBytes(byte[] bytes) throws IOException {
		getOutputStream().write(bytes);
	}

	@Override
	public void writeString(String string) throws IOException {
		writeBytes(string.getBytes());
	}

	@Override
	public byte readByte() throws IOException {
		return (byte)getInputStream().read();
	}

	@Override
	public int readBytes(byte[] buffer) throws IOException {
		return getInputStream().read(buffer);
	}

	@Override
	public String readString() throws IOException {
		return BulldogUtil.convertStreamToString(getInputStream());
	}

	@Override
	public OutputStream getOutputStream() throws IOException {
		return outputStream;
	}

	@Override
	public InputStream getInputStream() throws IOException {
		return inputStream;
	}
	
	public Pin[] getDataPins() {
		return dataPins;
	}
	
	public void enable() {
		DigitalOutput out = enablePin.as(DigitalOutput.class);
		out.applySignal(Signal.High);
		out.applySignal(Signal.Low);
	}
	
}
