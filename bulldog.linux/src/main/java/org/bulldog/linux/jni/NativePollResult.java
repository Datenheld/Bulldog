package org.bulldog.linux.jni;

import org.bulldog.core.Edge;

public class NativePollResult {

	private int events;
	private String filename;
	private String data;
	
	public NativePollResult(String filename, int events, String data) {
		this.filename = filename;
		this.events = events;
		this.data = data;
	}

	public int getEvents() {
		return events;
	}
	
	public String getFilename() {
		return filename;
	}
	
	public String getData() {
		return data;
	}
	
	public Edge getEdge() {
		if(getData() == null) { return null; }
		if(getData().charAt(0) == '1') {
			return Edge.Rising;
		} 
		
		return Edge.Falling;
	}
}

