package org.bulldog.beagleboneblack.sysfs;

import org.bulldog.core.Polarity;


public class SysFsPwm {

	private BBBSysFs sysfsUtil = new BBBSysFs();
	private String directory = null;
	private int slot = -1;
	
	public SysFsPwm(String path, int slot) {
		this.directory = path;
		this.slot = slot;
	}
	
	public void setPeriod(long period) {
		sysfsUtil.echo(directory + "/period", String.valueOf(period));
	}
	
	public void setDuty(long duty) {
		sysfsUtil.echo(directory + "/duty", String.valueOf(duty));
	}
	
	public void setPolarity(Polarity polarity) {
		sysfsUtil.echo(directory + "/polarity", polarity == Polarity.Negative ? "0" : "1");
	}
	
	public void enable() {
		sysfsUtil.echo(directory + "/run", "1");
	}
	
	public void disable() {
		sysfsUtil.echo(directory + "/run", "0");
	}
	
	public int getSlot() {
		return slot;
	}
	
	public String getDirectory() {
		return directory;
	}
	
}
