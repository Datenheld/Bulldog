package org.bulldog.linux.sysinfo;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bulldog.linux.util.Commands;

public class KernelInfo {
	
	public static String getName() {
		return Commands.shellExecute("uname", "-s");
	}
	
	public static String getNodeName() {
		return Commands.shellExecute("uname", "-n");
	}
	
	public static String getRelease() {
		return Commands.shellExecute("uname", "-r");
	}
	
	public static String getVersion() {
		return Commands.shellExecute("uname", "-v");
	}
	
	public static String getMachine() {
		return Commands.shellExecute("uname", "-m");
	}
	
	public static String getProcessor() {
		return Commands.shellExecute("uname", "-p");
	}
	
	public static String getOperatingSystem() {
		return Commands.shellExecute("uname", "-o");
	}
	
	public static int getArchitectureVersion() {
		return Integer.parseInt(getRelease().split("\\.")[0]);
	}
	
	public static int getMajorRelease() {
		return Integer.parseInt(getRelease().split("\\.")[1]);
	}
	
	public static int getMinorRelease() {
		String minorRelease = getRelease().split("\\.")[2];
		Pattern pattern = Pattern.compile("-?\\d+");
		Matcher matcher = pattern.matcher(minorRelease);
		if(matcher.find()) {
			return Integer.parseInt(matcher.group());
		} else {
			return 0;
		}
	}
	
}
