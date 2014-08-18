package org.bulldog.core.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface IOPort {

	public String getName();
	public String getAlias();
	public void setAlias(String alias);
	
	public void open() throws IOException;
	public boolean isOpen();
	public void close() throws IOException;
	
	public void writeByte(int b) throws IOException;
	public void writeBytes(byte[] bytes) throws IOException;
	public void writeString(String string) throws IOException;
	
	public byte readByte() throws IOException;
	public int readBytes(byte[] buffer) throws IOException;
	public String readString() throws IOException;
	
	public OutputStream getOutputStream() throws IOException;
	public InputStream getInputStream() throws IOException;
	
	
}
