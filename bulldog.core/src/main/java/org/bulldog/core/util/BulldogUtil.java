package org.bulldog.core.util;

import java.util.Scanner;

public final class BulldogUtil {

	public static void sleepMs(int ms) {
		try {
			Thread.sleep(ms);
		} catch(Exception ex) {
			
		}
	}
	
	public static String bytesToString(byte[] bytes, String encoding)  {
		if(bytes == null) {
			throw new IllegalArgumentException("bytes may not be null in string conversion");
		}

		if(bytes.length == 0) { return null; }
		
		try {
			return new String(bytes, encoding);
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		return null;
	}
	
	public static String bytesToString(byte[] bytes)  {
		return bytesToString(bytes, "ASCII");
	}
	
	@SuppressWarnings("resource")
	public static String convertStreamToString(java.io.InputStream is) {
	    Scanner s = new Scanner(is).useDelimiter("\\A");
	    return s.hasNext() ? s.next() : "";
	}
}
