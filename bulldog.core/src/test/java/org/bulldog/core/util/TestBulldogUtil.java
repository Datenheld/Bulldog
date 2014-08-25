package org.bulldog.core.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import junit.framework.TestCase;

import org.junit.Test;

public class TestBulldogUtil {

	@Test
	public void testSleep() {
		long start = System.currentTimeMillis();
		BulldogUtil.sleepMs(10);
		long delta = System.currentTimeMillis() - start;
		TestCase.assertEquals(10, delta, 5);
		
		start = System.currentTimeMillis();
		BulldogUtil.sleepMs(1);
		delta = System.currentTimeMillis() - start;
		TestCase.assertEquals(1, delta, 5);
		
		start = System.currentTimeMillis();
		BulldogUtil.sleepMs(100);
		delta = System.currentTimeMillis() - start;
		TestCase.assertEquals(100, delta, 5);
		
		start = System.currentTimeMillis();
		BulldogUtil.sleepMs(500);
		delta = System.currentTimeMillis() - start;
		TestCase.assertEquals(500, delta, 5);
		
		start = System.currentTimeMillis();
		BulldogUtil.sleepMs(25);
		delta = System.currentTimeMillis() - start;
		TestCase.assertEquals(25, delta, 5);
	}
	
	@Test
	public void testSleepNs() {
		// This is hardly testable and won't be precise anyway.
		// The method call alone will change results drastically
	}
	
	@Test 
	public void testBytesToString() throws UnsupportedEncodingException {
		String string = BulldogUtil.bytesToString("Hello World".getBytes("ASCII"));
		TestCase.assertEquals("Hello World", string);
		
		string = BulldogUtil.bytesToString("Hello World".getBytes());
		TestCase.assertEquals("Hello World", string);
		
		string = BulldogUtil.bytesToString(new byte[] {});
		TestCase.assertNull(string);
		
		try {
			string = BulldogUtil.bytesToString(null);
			TestCase.fail();
		} catch(IllegalArgumentException ex) {  }
		
		try {
			string = BulldogUtil.bytesToString("Hello World".getBytes("ASCII"), "NON_EXISTENT_ENCODING");
		} catch(IllegalArgumentException ex) {
			TestCase.assertEquals("Unknown encoding", ex.getMessage());
		}
	}
	
	@Test
	public void testStreamToString() throws IOException {
		String text = "Hello World\n\tasdasdasdas";
		String text2 = "Han Solo is the best star wars character";
		File file = File.createTempFile("TEST", ".txt");
		file.deleteOnExit();
		BufferedWriter writer = new BufferedWriter(new FileWriter(file));
		writer.write(text);
		writer.flush();
		FileInputStream inputStream = new FileInputStream(file);
		String readString = BulldogUtil.convertStreamToString(inputStream);
		TestCase.assertEquals(text, readString);
		
		readString = BulldogUtil.convertStreamToString(inputStream);
		TestCase.assertEquals("", readString);
		
		writer.write(text2);
		writer.flush();
		
		readString = BulldogUtil.convertStreamToString(inputStream);
		TestCase.assertEquals(text2, readString);
		
		inputStream.close();
		
		readString = BulldogUtil.convertStreamToString(inputStream);
		TestCase.assertEquals("", readString);
		
		writer.close();
	}
	
	@Test
	public void testReadFileAsString() throws IOException { 
		String text = "Hello World\n\tasdasdasdas";
		String text2 = "Han Solo is the best star wars character";
		File file = File.createTempFile("TEST", ".txt");
		file.deleteOnExit();
		BufferedWriter writer = new BufferedWriter(new FileWriter(file));
		writer.write(text);
		writer.flush();
		String readString = BulldogUtil.readFileAsString(file.getAbsolutePath());
		TestCase.assertEquals(text, readString);
		
		readString = BulldogUtil.readFileAsString("DOES NOT EXIST");
		TestCase.assertNull(readString);;
		
		writer.write(text2);
		writer.flush();
		
		readString = BulldogUtil.readFileAsString(file.getAbsolutePath());
		TestCase.assertEquals(text + text2, readString);
				

		writer.close();
	}
}
