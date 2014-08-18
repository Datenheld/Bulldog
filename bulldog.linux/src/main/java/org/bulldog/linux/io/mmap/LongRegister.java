package org.bulldog.linux.io.mmap;

import org.bulldog.core.util.BitMagic;

public class LongRegister extends Register<Long> {

	public LongRegister(MemoryMap map, long address) {
		super(map, address, Long.class);
	}

	@Override
	public void setValue(Long value) {
		getMemoryMap().setLongValue(getAddress(), value);
	}

	@Override
	public Long getValue() {
		return getMemoryMap().getLongValueAt(getAddress());
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

