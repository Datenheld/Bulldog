package org.bulldog.cubieboard;

import org.bulldog.core.gpio.Pin;

public class CubieboardPin extends Pin {

    private String fsName;
    private boolean interrupt;

    public CubieboardPin(String name, int address, String port, int indexOnPort, String fsName, boolean interrupt) {
		super(name, address, port, indexOnPort);
		this.fsName = fsName;
        this.interrupt = interrupt;
	}

    public String getFsName() {
        return fsName;
    }

    public boolean isInterrupt() {
        return interrupt;
    }
}
