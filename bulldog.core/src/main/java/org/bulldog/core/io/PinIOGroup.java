package org.bulldog.core.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.bulldog.core.Signal;
import org.bulldog.core.gpio.DigitalIO;
import org.bulldog.core.util.BulldogUtil;

public class PinIOGroup implements IOPort {
	
	private DigitalIO[] dataPins;
	private DigitalIO enablePin;
	private PinIOInputStream inputStream;
	private PinIOOutputStream outputStream;
	
	private String alias;
	private String name;
	
	private int delayMs = 1;
	
	public PinIOGroup(DigitalIO enablePin, DigitalIO... dataPins) {
		this(enablePin, 1, dataPins);
	}
	
	public PinIOGroup(DigitalIO enablePin, int delayMs, DigitalIO... dataPins) {
		this.enablePin = enablePin;
		this.dataPins = dataPins;
		this.delayMs = delayMs;
		inputStream = new PinIOInputStream(this);
		outputStream = new PinIOOutputStream(this);
		this.name = buildName();
	}

	private String buildName() {
		StringBuilder builder = new StringBuilder();
		builder.append("PinIOGroup: \n");
		builder.append("[Enable] " + enablePin.getName() + "\n");
		for(int i = 0; i < dataPins.length; i++) {
			builder.append("[Data " + i + "] " + dataPins[i].getName() + "\n");
		}
		return builder.toString();
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
	
	public DigitalIO[] getDataPins() {
		return dataPins;
	}
	
	public void startEnable() {
		enablePin.applySignal(Signal.High);
		BulldogUtil.sleepMs(delayMs);
	}
	
	public void endEnable() {
		enablePin.applySignal(Signal.Low);
		BulldogUtil.sleepMs(delayMs);
	}
	
	@Override
	public String toString() {
		return getName();
	}
}
