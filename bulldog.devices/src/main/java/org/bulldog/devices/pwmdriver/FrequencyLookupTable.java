package org.bulldog.devices.pwmdriver;

import java.util.HashMap;

public class FrequencyLookupTable extends HashMap<Double, Integer> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4953943440394262798L;

	public FrequencyLookupTable() {
		this.put(50.0, 134);
	}
		
}
