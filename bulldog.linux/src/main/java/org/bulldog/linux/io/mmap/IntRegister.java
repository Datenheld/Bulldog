package org.bulldog.linux.io.mmap;

import org.bulldog.core.util.BitMagic;

public class IntRegister extends Register<Integer> {

	public IntRegister(MemoryMap map, long address) {
		super(map, address, Integer.class);
	}

	@Override
	public void setValue(Integer value) {
		getMemoryMap().setIntValue(getAddress(), value);
	}

	@Override
	public Integer getValue() {
		return getMemoryMap().getIntValueAt(getAddress());
	}
	
	@Override
	public void setBit(int index) {
		setValue(BitMagic.setBit(getValue(), index, 1));
	}

	@Override
	public void clearBit(int index) {
		setValue(BitMagic.setBit(getValue(), index, 0));
	}

	@Override
	public void toggleBit(int index) {
		setValue(BitMagic.toggleBit(getValue(), index));
	}

}
