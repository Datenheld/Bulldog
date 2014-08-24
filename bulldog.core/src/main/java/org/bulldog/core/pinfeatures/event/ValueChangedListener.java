package org.bulldog.core.pinfeatures.event;

public interface ValueChangedListener<T> {

	void valueChanged(T oldValue, T newValue);
}
