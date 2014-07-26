package org.bulldog.linux.io;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
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
	private List<DigitalOutput> selectedSlaveSelectPins = new ArrayList<DigitalOutput>();
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
		registerSlave(address);
		return new SpiConnection(this, address);
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
		registerSlave(address);
		if(getSelectedSlaveSelectPins().contains(output)) { return; }
		getSelectedSlaveSelectPins().add(output);
	}
	
	@Override
	public void selectSlave(DigitalOutput output) {
		registerSlave(output);
		if(getSelectedSlaveSelectPins().contains(output)) { return; }
		getSelectedSlaveSelectPins().add(output);
	}
	
	public void deselectSlave(int address) {
		DigitalOutput output = board.getPin(address).as(DigitalOutput.class);
		getSelectedSlaveSelectPins().remove(output);
	}
	
	@Override
	public void deselectSlave(DigitalOutput output) {
		selectedSlaveSelectPins.remove(output);
	}
	
	@Override
	public void registerSlave(DigitalOutput output) {
		if(!this.getSlaveSelectPins().contains(output)) {
			getSlaveSelectPins().add(output);
		}
	}

	@Override
	public void deregisterSlave(DigitalOutput output) {
		if(getSelectedSlaveSelectPins().contains(output)) { 
			getSelectedSlaveSelectPins().remove(output);
		}
		
		if(getSlaveSelectPins().contains(output)) {
			getSlaveSelectPins().remove(output);
		}
	}

	@Override
	public void registerSlave(int address) {
		DigitalOutput output = board.getPin(address).as(DigitalOutput.class);
		
		if(!this.getSlaveSelectPins().contains(output)) {
			getSlaveSelectPins().add(output);
		}
	}

	@Override
	public void deregisterSlave(int address) {
		DigitalOutput output = board.getPin(address).as(DigitalOutput.class);
		
		if(getSelectedSlaveSelectPins().contains(output)) { 
			getSelectedSlaveSelectPins().remove(output);
		}
		
		if(getSlaveSelectPins().contains(output)) {
			getSlaveSelectPins().remove(output);
		}
	}

	private void startOutput() {
		for(DigitalOutput output : this.getSelectedSlaveSelectPins()) {
			output.low();
		}
	}
	
	private void endOutput() {
		for(DigitalOutput output : this.getSelectedSlaveSelectPins()) {
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
		/*ByteBuffer rxBuffer = ByteBuffer.allocateDirect(2);
		rxBuffer.put(bytes);
		rxBuffer.rewind();
		NativeSpi.spiTransfer(this.getFileDescriptor(), rxBuffer, rxBuffer, 1, 0, 10000, 16);*/
		endOutput();
	}

	@Override
	public void writeString(String string) throws IOException {
		writeBytes(string.getBytes());
	}

	@Override
	public SpiConnection createSpiConnection(DigitalOutput output) {
		if(!getSlaveSelectPins().contains(output)) {
			getSlaveSelectPins().add(output);
		}
		
		return new SpiConnection(this, output.getPin().getAddress());
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
		return this.slaveSelectPins;
	}

	@Override
	public List<DigitalOutput> getSelectedSlaveSelectPins() {
		return this.selectedSlaveSelectPins;
	}

	@Override
	public void broadcast(byte data) throws IOException {
		for(DigitalOutput output : this.getSlaveSelectPins()) {
			selectSlave(output);
		}
		
		writeByte(data);
		
		for(DigitalOutput output : this.getSlaveSelectPins()) {
			deselectSlave(output);
		}
	}

	@Override
	public void broadcast(byte[] data) throws IOException {
		for(DigitalOutput output : this.getSlaveSelectPins()) {
			selectSlave(output);
		}
		
		writeBytes(data);
		
		for(DigitalOutput output : this.getSlaveSelectPins()) {
			deselectSlave(output);
		}
	}

	@Override
	public boolean isSlaveSelected(int address) {
		for(DigitalOutput output : selectedSlaveSelectPins) {
			if(output.getPin().getAddress() == address) {
				return true;
			}
		}
		
		return false;
	}
	
}
