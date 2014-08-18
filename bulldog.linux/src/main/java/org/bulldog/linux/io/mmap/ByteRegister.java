package org.bulldog.linux.io.mmap;

import org.bulldog.core.util.BitMagic;

public class ByteRegister extends Register<Byte> {

	public ByteRegister(MemoryMap map, long address) {
		super(map, address, Byte.class);
	}

	@Override
	public void setValue(Byte value) {
		getMemoryMap().setByteValue(getAddress(), value);
	}

	@Override
	public Byte getValue() {
		return getMemoryMap().getByteValueAt(getAddress());
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
