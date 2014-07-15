package com.theoriginalbit.minecraft.moarperipherals.terminal;

public class Terminal {
	private int width;
	private int height;
	private int cursorX = 0;
	private int cursorY = 0;
	
	public Terminal(int terminalWidth, int terminalHeight) {
		width = terminalWidth;
		height = terminalHeight;
	}
	
	public final int getWidth() {
		return width;
	}
	
	public final int getHeight() {
		return height;
	}
}
