package org.bulldog.linux.util;

import java.io.IOException;
import java.io.InputStream;

public class Commands {

	public static String cat(String filename) {
		return shellExecute("cat", filename);
	}
	
	public static String shellExecute(String command, String... args) {
		ProcessBuilder cmd;
		String result = "";
		
		try {
			String[] processInfo = new String[args.length + 1];
			processInfo[0] = command;
			for(int i = 0; i < args.length; i++) {
				processInfo[i + 1] = args[i];
			}
			cmd = new ProcessBuilder(processInfo);

			Process process = cmd.start();
			InputStream in = process.getInputStream();
			byte[] re = new byte[1024];
			while (in.read(re) != -1) {
				result = result + new String(re);
			}
			in.close();
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
		
		return result;
	}
}
