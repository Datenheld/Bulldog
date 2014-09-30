package org.bulldog.beagleboneblack.sysfs;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.bulldog.core.util.BulldogUtil;
import org.bulldog.linux.sysfs.SysFs;

public class BBBSysFs extends SysFs {

	private static final int WAIT_TIMEOUT_MS = 5000;
	
	private String SYSFS_DEVICES_PATH = "/sys/devices";
	
	public BBBSysFs() {
		
	}
	
	public File getCapeManager() {
		return getFilesInPath(SYSFS_DEVICES_PATH, "bone_capemgr")[0];
	}
	
	public File getCapeManagerSlots() {
		return getFilesInPath(getCapeManager().getAbsolutePath(), "slots")[0];
	}
	
	public int getSlotNumber(String namePattern) {
		List<String> slots = readSlots();
		for(int i = 0; i < slots.size(); i++) {
			String slotContent = slots.get(i);
			if(slotContent.contains(namePattern)) {
				return Integer.parseInt(slotContent.substring(0, slotContent.indexOf(":")).trim());
			}
		}
		
		return -1;
	}
	
	public boolean isSlotLoaded(int slotIndex) {
		if(slotIndex < 0) {
			return false;
		}
		
		List<String> slots = readSlots();
		if(slotIndex >= slots.size()) {
			return false;
		}
		
		String slot = slots.get(slotIndex);
		
		if(slot.length() > 11) {
			return slot.charAt(11) == 'L';
		} else {
			return false;
		}
	}
	
	public List<String> readSlots() {
		List<String> buffer = new ArrayList<String>();
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(getCapeManagerSlots()));
			String line = null;
			while((line = reader.readLine()) != null) {
				buffer.add(line);
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return buffer;
	}
	
	public File findOcpDevice(final String namePattern) {
		File[] ocpDirectories = getFilesInPath(SYSFS_DEVICES_PATH, "ocp");
		for(File file : ocpDirectories) {
			File[] files = getFilesInPath(file.getAbsolutePath(), namePattern);
			if(files != null && files.length > 0) {
				return files[0];
			}
			
		}
		return null;
	}
	
	public void removeSlot(int number) {
		echoAndWait(getCapeManagerSlots().getAbsolutePath(), "-" + number, 10);
	}
	
	public void createSlotIfNotExists(String deviceName) {
		if(getSlotNumber(deviceName) < 0) {
			echoAndWait(getCapeManagerSlots().getAbsolutePath(), deviceName, 10);
			waitForSlotCreation(deviceName, WAIT_TIMEOUT_MS);
		} 
	}

	private void waitForSlotCreation(String deviceName, long waitMillis) {
		long startWaitingTime = System.currentTimeMillis();
		while(getSlotNumber(deviceName) < 0) {
			//wait until the device appears
			BulldogUtil.sleepMs(10);
			long millisecondsInWait = System.currentTimeMillis()  - startWaitingTime;
			if(millisecondsInWait >= waitMillis) {
				throw new RuntimeException("Could not create device for " + deviceName + " within " + waitMillis + " milliseconds. Aborting.");
			}
		}
	}
	
	
	public void removeSlotOfDevice(String deviceName) {
		int slotNumber = getSlotNumber(deviceName);
		if(slotNumber >= 0) {
			removeSlot(slotNumber);
		}
	}
}
