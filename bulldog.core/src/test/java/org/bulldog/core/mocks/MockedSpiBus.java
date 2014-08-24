package org.bulldog.core.mocks;

import java.io.IOException;
import java.util.List;

import org.bulldog.core.io.bus.spi.SpiBus;
import org.bulldog.core.io.bus.spi.SpiConnection;
import org.bulldog.core.io.bus.spi.SpiMessage;
import org.bulldog.core.io.bus.spi.SpiMode;
import org.bulldog.core.pinfeatures.DigitalOutput;
import org.bulldog.core.pinfeatures.Pin;

public class MockedSpiBus extends MockedBus implements SpiBus {

	public MockedSpiBus(String name) {
		super(name);
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
	public List<DigitalOutput> getSlaveSelectPins() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getSpeedInHz() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setSpeedInHz(int hz) {
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
	public void setMode(SpiMode mode) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public SpiMode getMode() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void useLeastSignificantBitFirst() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void useMostSignificantBitFirst() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isLSBUsed() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isMSBUsed() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void selectSlave(DigitalOutput chipSelect) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void selectSlaves(DigitalOutput... chipSelects) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void selectSlaves(Integer... chipSelectAddresses) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public SpiConnection createSpiConnection() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SpiConnection createSpiConnection(int chipSelectAddress) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SpiConnection createSpiConnection(DigitalOutput chipSelect) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SpiConnection createSpiConnection(DigitalOutput... chipSelects) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SpiConnection createSpiConnection(int... chipSelectAddress) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void broadcast(byte[] bytes, DigitalOutput... chipSelects)
			throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public SpiMessage transfer(byte[] buffer) {
		// TODO Auto-generated method stub
		return null;
	}

}
