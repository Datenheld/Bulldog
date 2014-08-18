package org.bulldog.linux.io.mmap;

import org.bulldog.core.util.BitMagic;

public class ShortRegister extends Register<Short> {

	public ShortRegister(MemoryMap map, long address) {
		super(map, address, Short.class);
	}

	@Override
	public void setValue(Short value) {
		getMemoryMap().setShortValue(getAddress(), value);
	}

	@Override
	public Short getValue() {
		return getMemoryMap().getShortValueAt(getAddress());
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
