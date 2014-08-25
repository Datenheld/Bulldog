package org.bulldog.core.gpio.event;

public interface ValueChangedListener<T> {

	void valueChanged(T oldValue, T newValue);
}
