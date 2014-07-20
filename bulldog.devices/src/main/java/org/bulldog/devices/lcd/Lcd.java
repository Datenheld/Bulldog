package org.bulldog.devices.lcd;

public interface Lcd {

	void write(String text);
	void writeAt(int row, int column, String text);
	void clear();
	void blinkCursor(boolean blink);
	void showCursor(boolean show);
	void home();
	void on();
	void off();
	void setCursorPosition(int row, int column);
	String read();
}
