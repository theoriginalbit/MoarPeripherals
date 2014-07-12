package com.theoriginalbit.minecraft.moarperipherals.gui;

public enum GuiType {
	KEYBOARD, KEYBOARD_MODIFY;

	public static GuiType valueOf(int id) {
		for (GuiType gui : values()) {
			if (gui.ordinal() == id) {
				return gui;
			}
		}
		return null;
	}
}