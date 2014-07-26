package org.bulldog.linux.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.bulldog.core.Polarity;
import org.bulldog.core.gpio.Pin;
import org.bulldog.core.io.bus.BusConnection;
import org.bulldog.core.io.bus.spi.SpiBus;
import org.bulldog.core.io.bus.spi.SpiConnection;

public class LinuxSpiBus implements SpiBus {

	@Override
	public void selectAddress(int address) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getSelectedAddress() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public BusConnection createConnection(int address) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getAlias() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setAlias(String alias) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void open() throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isOpen() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void close() throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void writeByte(int b) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void writeBytes(byte[] bytes) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void writeString(String string) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public byte readByte() throws IOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int readBytes(byte[] buffer) throws IOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String readString() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OutputStream getOutputStream() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InputStream getInputStream() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Pin getMISO() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Pin getMOSI() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Pin getSCLK() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Pin> getSS() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getFrequency() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setFrequency(float frequency) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setBitsPerWord(int bpw) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getBitsPerWord() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setDelayMicroseconds(int delay) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getDelayMicroseconds() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setClockphase() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getClockphase() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setMode(int mode) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getMode() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setClockPolarity(Polarity polarity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Polarity getClockPolarity() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SpiConnection createSpiConnection(int address) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SpiConnection createSpiConnection(Pin slaveSelect) {
		// TODO Auto-generated method stub
		return null;
	}




}
