package org.bulldog.linux.io;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bulldog.core.gpio.DigitalOutput;
import org.bulldog.core.io.bus.BusConnection;
import org.bulldog.core.io.bus.spi.SpiBus;
import org.bulldog.core.io.bus.spi.SpiConnection;
import org.bulldog.core.io.bus.spi.SpiMessage;
import org.bulldog.core.platform.Board;
import org.bulldog.core.util.BulldogUtil;
import org.bulldog.linux.jni.NativeSpi;

public class LinuxSpiBus extends AbstractLinuxBus implements SpiBus {
	
	private List<DigitalOutput> slaveSelectPins = new ArrayList<DigitalOutput>();
	private Board board;
	
	public LinuxSpiBus(String name, String deviceFilePath, Board board) {
		super(name, deviceFilePath);
		this.board = board;
	}
	
	public BusConnection createConnection(int address) {
		return createSpiConnection(address);
	}

	@Override
	public SpiConnection createSpiConnection(int address) {
		return new SpiConnection(this, address);
	}
	
	@Override
	public SpiConnection createSpiConnection(DigitalOutput output) {		
		return new SpiConnection(this, output.getPin().getAddress());
	}
	
	@Override
	public SpiConnection createSpiConnection(DigitalOutput... chipSelects) {
		return new SpiConnection(this, chipSelects);
	}
	
	@Override
	public SpiConnection createSpiConnection(int... addresses) {
		return new SpiConnection(this, addresses);
	}
	

	public byte readByte() throws IOException {
		try {
			byte[] buffer = new byte[1];
			getInputStream().read(buffer);
			return buffer[0];
		} catch(Exception ex) {
			throw new IOException(ERROR_READING_BYTE);
		}
	}
	
	protected int openImpl() {
		return NativeSpi.spiOpen(getDeviceFilePath(), 10000, 8, 0);
	}
	
	protected int closeImpl() {
		return NativeSpi.spiClose(getFileDescriptor());
	}

	@Override
	public int readBytes(byte[] buffer) throws IOException {
		return getInputStream().read(buffer);
	}

	@Override
	public String readString() throws IOException {
		return BulldogUtil.convertStreamToString(getInputStream());
	}

	public void selectSlave(int address) throws IOException {
		DigitalOutput output = board.getPin(address).as(DigitalOutput.class);
		selectSlave(output);
	}
	
	
	@Override
	public void selectSlave(DigitalOutput output) {
		getSlaveSelectPins().clear();
		getSlaveSelectPins().add(output);
	}
	
	@Override
	public void selectSlaves(DigitalOutput... chipSelects) {
		getSlaveSelectPins().clear();
		getSlaveSelectPins().addAll(Arrays.asList(chipSelects));
	}
	

	@Override
	public void selectSlaves(Integer... chipSelectAddresses) {
		List<DigitalOutput> outputs = new ArrayList<DigitalOutput>();
		for(Integer cs : chipSelectAddresses) {
			DigitalOutput output = board.getPin(cs).as(DigitalOutput.class);
			outputs.add(output);
		}
		selectSlaves(outputs.toArray(new DigitalOutput[outputs.size()]));
	}
	
	private void startOutput() {
		for(DigitalOutput output : getSlaveSelectPins()) {
			output.low();
		}
	}
	
	private void endOutput() {
		for(DigitalOutput output : getSlaveSelectPins()) {
			output.high();
		}
	}
	
	public void writeByte(int b) throws IOException {
		try {
			startOutput();
			getOutputStream().write(b);
			getOutputStream().flush();
			endOutput();
		} catch(Exception ex) {
			throw new IOException(ERROR_WRITING_BYTE);
		}
	}
	
	@Override
	public void writeBytes(byte[] bytes) throws IOException {
		startOutput();
		//this.transfer(bytes);
		getOutputStream().write(bytes);
		getOutputStream().flush();
		//ByteBuffer rxBuffer = ByteBuffer.allocateDirect(2);
		//rxBuffer.put(bytes);
		//rxBuffer.rewind();
		//NativeSpi.spiTransfer(this.getFileDescriptor(), rxBuffer, rxBuffer, 1, 0, 10000, 16);
		endOutput();
	}

	@Override
	public void writeString(String string) throws IOException {
		writeBytes(string.getBytes());
	}

	@Override
	public SpiMessage transfer(byte[] data) {
		ByteBuffer buffer = ByteBuffer.allocateDirect(data.length);
		ByteBuffer recvBuffer = ByteBuffer.allocateDirect(data.length);
		buffer.put(data);
		buffer.rewind();
		NativeSpi.spiTransfer(this.getFileDescriptor(), buffer, recvBuffer, 1, 0, 10000, 16);
		return null;
	}

	@Override
	public List<DigitalOutput> getSlaveSelectPins() {
		return slaveSelectPins;
	}

	@Override
	public boolean isSlaveSelected(int address) {
		for(DigitalOutput output : getSlaveSelectPins()) {
			if(output.getPin().getAddress() == address) {
				return true;
			}
		}
		
		return false;
	}

	@Override
	public void broadcast(byte[] bytes, DigitalOutput... chipSelects) throws IOException {
		selectSlaves(chipSelects);
		writeBytes(bytes);
	}


}
