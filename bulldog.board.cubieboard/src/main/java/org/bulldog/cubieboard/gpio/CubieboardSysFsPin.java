package org.bulldog.cubieboard.gpio;

import org.bulldog.linux.sysfs.SysFsPin;

public class CubieboardSysFsPin extends SysFsPin {
    private static final String directory = "/sys/class/gpio";

    private String fsName;
    private boolean interrupt;

    public CubieboardSysFsPin(int pin, String fsName, boolean interrupt) {
        super(pin);
        this.fsName = fsName;
        this.interrupt = interrupt;
    }

    @Override
    public String getPinDirectory() {
        return directory + "/gpio" + fsName + "/";
    }

    @Override
    public void setEdge(String edge) {
        if (interrupt)
            super.setEdge(edge);
    }
}
