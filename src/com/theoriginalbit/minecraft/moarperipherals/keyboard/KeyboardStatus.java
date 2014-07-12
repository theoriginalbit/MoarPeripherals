package com.theoriginalbit.minecraft.moarperipherals.keyboard;

import net.minecraft.util.StatCollector;

public enum KeyboardStatus {
	OTHER("moarperipherasl.gui.keyboard.status.other"),
	EDITING("moarperipherals.gui.keyboard.status.editing"),
	OUT_OF_RANGE("moarperipherasl.gui.keyboard.status.outofrange", 0xec960e),
	INVALID("moarperipherasl.gui.keyboard.status.invalid", 0xcc0e0e),
	CONNECTED("moarperipherasl.gui.keyboard.status.connected", 0x0f9501),
	DISCONNECTED("moarperipherasl.gui.keyboard.status.disconnected");

	private final String raw;
	private final int color;

	private KeyboardStatus(String text) {
		this(text, 0x404040);
	}

	private KeyboardStatus(String text, int col) {
		raw = text;
		color = col;
	}
	
	/**
	 * Returns the localised version of the text to display on the GUI
	 */
	public String getLocal() {
		return StatCollector.translateToLocal(raw);
	}
	
	/**
	 * Returns the colour the text should display on the GUI
	 */
	public int getColor() {
		return color;
	}
}