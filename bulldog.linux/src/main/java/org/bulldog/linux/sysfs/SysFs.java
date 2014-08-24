package org.bulldog.linux.sysfs;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileWriter;

import org.bulldog.core.util.BulldogUtil;

public class SysFs {

	private static final int WAIT_TIMEOUT_MS = 5000;
	
	public SysFs() {
		
	}
	
	public File[] getFilesInPath(String path, final String namePattern) {
		File root = new File(path);
		File[] files = root.listFiles(new FileFilter() {  
		    public boolean accept(File pathname) {  
		        return pathname.getName().startsWith(namePattern);
		    }  
		});
		
		return files;
	}
	
	public void echo(String path, Object value) {
		echo(path, String.valueOf(value));
	}
	
	public void echo(String path, String value) {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(path));
			writer.write(value);
			writer.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public void echoAndWait(String path, Object value, int waitMs) {
		echoAndWait(path, String.valueOf(value), waitMs);
	}
	 
	public void echoAndWait(String path, String value, int waitMs) {
		try {
			waitForFileCreation(path, WAIT_TIMEOUT_MS);
			BufferedWriter writer = new BufferedWriter(new FileWriter(path));
			writer.write(value);
			BulldogUtil.sleepMs(waitMs);
			writer.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public void waitForFileCreation(String filePath, long waitMillis) {
		long startWaitingTime = System.currentTimeMillis();
		File file = new File(filePath);
		while(!file.exists()) {
			BulldogUtil.sleepMs(10);
			
			long millisecondsInWait = System.currentTimeMillis()  - startWaitingTime;
			if(millisecondsInWait >= waitMillis) {
				throw new RuntimeException("Could not find file " + filePath + " within " + waitMillis + " milliseconds. Aborting.");
			}
		}
	}
	
}
