package org.bulldog.core.gpio.event;

import org.bulldog.core.Edge;
import org.bulldog.core.gpio.Pin;

public class InterruptEventArgs {

	private Edge edge;
	
	public InterruptEventArgs(Pin pin, Edge edge) {
		this.edge = edge;
	}
	
	public Edge getEdge() {
		return edge;
	}
	
}
