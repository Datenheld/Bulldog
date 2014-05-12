package org.bulldog.core.gpio.event;

import org.bulldog.core.Edge;

public class InterruptEventArgs {

	private Edge edge;
	
	public InterruptEventArgs(Edge edge) {
		this.edge = edge;
	}
	
	public Edge getEdge() {
		return edge;
	}
	
}
