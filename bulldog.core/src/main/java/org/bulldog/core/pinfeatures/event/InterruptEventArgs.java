package org.bulldog.core.pinfeatures.event;

import org.bulldog.core.Edge;
import org.bulldog.core.pinfeatures.Pin;

public class InterruptEventArgs {

	private Edge edge;
	
	public InterruptEventArgs(Pin pin, Edge edge) {
		this.edge = edge;
	}
	
	public Edge getEdge() {
		return edge;
	}
	
}
