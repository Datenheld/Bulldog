package org.bulldog.beagleboneblack.io;

import java.io.IOException;

import org.bulldog.beagleboneblack.sysfs.BBBSysFs;
import org.bulldog.core.gpio.Pin;
import org.bulldog.core.io.uart.UartPort;
import org.bulldog.core.io.uart.UartRx;
import org.bulldog.core.io.uart.UartSignalType;
import org.bulldog.core.io.uart.UartTx;
import org.bulldog.linux.io.LinuxSerialPort;

public class BBBUartPort extends LinuxSerialPort implements UartPort {
	
	private Pin rxPin;
	private Pin txPin;
	private String slotName;
	private String name;
	private BBBSysFs sysFs = new BBBSysFs();
	private boolean setupInProgress = false;
	
	public BBBUartPort(String name, String filename, String slotName, Pin rx, Pin tx) {
		super(filename);
		this.slotName = slotName;
		this.rxPin = rx;
		this.txPin = tx;
		this.slotName = slotName;
		this.name = name;
		addRxFeatureToPin(rx);
		addTxFeatureToPin(tx);
	}
	
	@Override
	public void open() throws IOException {
		setup();
		super.open();
	}
	
	@Override
	public void close() throws IOException {
		super.close();
		teardown();
	}
	
	public boolean isSlotLoaded() {
		return sysFs.getSlotNumber(getSlotName()) >= 0;
	}
	
	public void setup() {
		if(setupInProgress) { return; }
		setupInProgress = true;
		sysFs.createSlotIfNotExists(getSlotName());
		if(getRx() != null) {
			getRx().activateFeature(UartTx.class);
		} 
		if(getTx() != null) {
			getTx().activateFeature(UartRx.class);
		}
		setupInProgress = false;
	}
	
	public void teardown() {
		System.out.println("Cannot remove UART '"+getName()+"' from device tree;\nIt will crash the bone.");
	}
	
	private void addRxFeatureToPin(Pin pin) {
		if(pin == null) { return; }
		pin.addFeature(new BBBUartPinFeature(this, pin, UartSignalType.RX));
	}
	
	private void addTxFeatureToPin(Pin pin) {
		if(pin == null) { return; }
		pin.addFeature(new BBBUartPinFeature(this, pin, UartSignalType.TX));
	}

	@Override
	public Pin getRx() {
		return rxPin;
	}

	@Override
	public Pin getTx() {
		return txPin;
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	public String getSlotName() {
		return slotName;
	}

}
