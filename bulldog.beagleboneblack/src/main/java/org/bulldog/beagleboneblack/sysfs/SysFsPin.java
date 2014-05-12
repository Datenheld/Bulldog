package org.bulldog.beagleboneblack.sysfs;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.bulldog.core.util.BulldogUtil;



public class SysFsPin {

	private SysFs sysfsUtil = new SysFs();
	private String directory = "/sys/class/gpio";
	private int pin = 0;
	
	public SysFsPin(int pin) {
		this.pin = pin;
	}
	
	public boolean isExported() {
		return new File(getPinDirectory()).exists();
	}
	
	public void exportIfNecessary() {
		if(!isExported()) {
			sysfsUtil.echo(directory + "/export", getPin());
			File file = new File(this.getValueFilePath());
			long startTime = System.currentTimeMillis();
			long delta = 0;
			while(!file.exists()) {
				BulldogUtil.sleepMs(10);
				delta = System.currentTimeMillis() - startTime;
				if(delta >= 10000) {
					throw new RuntimeException("Could not create pin - waited 10 seconds. Aborting.");
				}
			}
		}
	}
	
	public void unexport() {
		if(isExported()) {
			sysfsUtil.echo(directory + "/unexport", getPin());
		}
	}
	
	public void setEdge(String edge) {
		sysfsUtil.echo(getPinDirectory() + "edge", edge);
	}
	
	public void setDirection(String direction) {
		sysfsUtil.echo(getPinDirectory() + "direction", direction);
	}
	
	public String getPinDirectory() {
		return  directory + "/gpio" + getPin() + "/";
	}
	
	public String getValueFilePath() {
		return getPinDirectory() + "value";
	}
	
	private int getPin() {
		return pin;
	}
	
	public FileInputStream getValueInputStream() throws FileNotFoundException {
		FileInputStream stream = new FileInputStream(getValueFilePath());
		return stream;
	}

}
