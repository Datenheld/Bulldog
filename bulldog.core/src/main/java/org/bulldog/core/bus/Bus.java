package org.bulldog.core.bus;

import java.io.IOException;

public interface Bus {

	public String getName();
	public String getAlias();
	public void setAlias(String alias);
	
	public void open() throws IOException;
	public boolean isOpen();
	public void close() throws IOException;
	
	public void writeByte(byte b) throws IOException;
	public byte readByte() throws IOException;
	
	public void selectAddress(int address) throws IOException;
	public int getSelectedAddress();	
	
	public BusConnection createConnection(int address);

}
