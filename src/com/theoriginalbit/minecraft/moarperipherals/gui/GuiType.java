package com.theoriginalbit.minecraft.moarperipherals.gui;

public enum GuiType {
	KEYBOARD;

	public static GuiType valueOf(int id) {
		for (GuiType gui : values()) {
			if (gui.ordinal() == id) {
				return gui;
			}
		}
		return null;
	}
}