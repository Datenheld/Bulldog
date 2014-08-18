package org.bulldog.linux.util;

import java.io.File;
import java.security.CodeSource;

public class LinuxLibraryLoader {

	public static void loadNativeLibrary() {
		String codeDirectory =  getJarFileDirectory();
		File file = new File(codeDirectory + "/libbulldog-linux.so");
		if(file.exists()) {
			System.load(file.getAbsolutePath());
		}  else {
			System.loadLibrary("bulldog-linux");
		}
	}
	
	private static String getJarFileDirectory() {
		try {
			CodeSource codeSource = LinuxLibraryLoader.class.getProtectionDomain().getCodeSource();
			File jarFile = new File(codeSource.getLocation().toURI().getPath());
			String jarDir = jarFile.getParentFile().getPath();
			return jarDir;
		} catch(Exception ex) {
			return null;
		}
	}
	
}
