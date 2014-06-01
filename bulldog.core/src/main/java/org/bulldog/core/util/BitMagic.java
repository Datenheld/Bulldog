package org.bulldog.core.util;

public class BitMagic {

	public static int getBit(int dword, int bitPosition) {
		return (dword >> bitPosition) & 1;
	}
	
	public static int setBit(int dword, int bitPosition, int value) {
		if(value == 1) {
			dword |= (value << bitPosition);
		} else {
			dword &= ~(1 << bitPosition);
		}
		return dword;
	}
	
	public static boolean isBitSet(int dword, int bitPosition){
	    return ((dword & (1 << bitPosition)) != 0);
	}
	
	public static int toggleBit(int dword, int bitPosition) {
		dword ^= (1 << bitPosition);
		return dword;
	}
	
}
