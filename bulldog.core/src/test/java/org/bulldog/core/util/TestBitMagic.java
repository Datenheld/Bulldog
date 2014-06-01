package org.bulldog.core.util;

import junit.framework.TestCase;

import org.junit.Test;

public class TestBitMagic {

	@Test
	public void testBitManipulations() {
		int testValue = 0b00000000;
		testValue = BitMagic.setBit(testValue, 0, 1);
		TestCase.assertEquals(0b00000001, testValue);
		testValue = BitMagic.setBit(testValue, 1, 1);
		TestCase.assertEquals(0b00000011, testValue);
		testValue = BitMagic.setBit(testValue, 0, 0);
		TestCase.assertEquals(0b00000010, testValue);
		testValue = BitMagic.toggleBit(testValue, 2);
		TestCase.assertEquals(0b00000110, testValue);
		testValue = BitMagic.toggleBit(testValue, 1);
		TestCase.assertEquals(0b00000100, testValue);
		
		testValue = 0b101010;
		for(int i = 0; i < 6; i++) {
			if(i%2 == 0) {
				TestCase.assertEquals(0, BitMagic.getBit(testValue, i));
				TestCase.assertFalse(BitMagic.isBitSet(testValue, i));
			} else {
				TestCase.assertEquals(1, BitMagic.getBit(testValue, i));
				TestCase.assertTrue(BitMagic.isBitSet(testValue, i));
			}
		}
	}
	
	@Test
	public void testBitToString() {
		TestCase.assertEquals("00000000", BitMagic.toBitString((byte)0b00000000));
		TestCase.assertEquals("00100010", BitMagic.toBitString((byte)0b00100010));
		TestCase.assertEquals("11111111", BitMagic.toBitString((byte)0b11111111));
	}
}
