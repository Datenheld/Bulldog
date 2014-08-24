package org.bulldog.linux.sysinfo;

import java.util.HashMap;

import org.bulldog.linux.util.Commands;

public class CpuInfo {

	private static final String CPU_REVISION = "CPU revision";
	
	private static HashMap<String, String> properties = new HashMap<String, String>();
	
	static {
		String cpuInfo = Commands.cat("/proc/cpuinfo");
		String[] infos = cpuInfo.split("\n");
		for(String info : infos) {
			String[] tokens = info.split(":");
			if(tokens.length >= 2) {
				System.out.println(tokens[0].trim() + " : " + tokens[1].trim());
				properties.put(tokens[0].trim(), tokens[1].trim());
			}
		}
	}
	
	public static String getCPURevision() {
		return properties.get(CPU_REVISION);
	}
	
}
