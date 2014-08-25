package org.bulldog.devices.lcd;

public interface Lcd {

	void setMode(LcdMode mode, LcdFont font);
	void write(String text);
	void writeAt(int row, int column, String text);
	void clear();
	void blinkCursor(boolean blink);
	void showCursor(boolean show);
	void home();
	void on();
	void off();
	void setCursorPosition(int line, int column);
	String readLine(int line);
	String read(int length);
	String read(int line, int column, int length);
}
