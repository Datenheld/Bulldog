package org.bulldog.linux.io.mmap;

public abstract class Register<T extends Number> {

	private MemoryMap map;
	private long address;
	private Class<T> clazz;
	
	public Register(MemoryMap map, long address, Class<T> clazz) {
		this.map = map;
		this.address = address;
		this.clazz = clazz;
	}
	
	public abstract void setValue(T value);
	public abstract T getValue();
	public abstract void setBit(int index);
	public abstract void clearBit(int index);
	public abstract void toggleBit(int index);
	
	public Class<T> getContentClass() {
		return clazz;
	}
	
	public MemoryMap getMemoryMap() {
		return map;
	}
	
	public long getAddress() {
		return address;
	}
	
	@SuppressWarnings("unchecked")
	public void xor(T val) {
		long currentValue = getValue().longValue();
		currentValue ^= val.longValue();
		this.setValue((T) val.getClass().cast(currentValue));
	}
	
	@SuppressWarnings("unchecked")
	public void or(T val) {
		long currentValue = getValue().longValue();
		currentValue |= val.longValue();
		this.setValue((T) val.getClass().cast(currentValue));
	}
	
	@SuppressWarnings("unchecked")
	public void and(T val) {
		long currentValue = getValue().longValue();
		currentValue &= val.longValue();
		this.setValue((T) val.getClass().cast(currentValue));
	}
	
		
}
