package org.bulldog.core.mocks;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.bulldog.core.io.IOPort;

public class MockedIOPort implements IOPort {
	
	private String name;
	
	public MockedIOPort(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
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

}
