package com.theoriginalbit.minecraft.moarperipherals.keyboard;

public enum KeyboardEvent {
	KEY_PRESS, TERMINATE, TURN_ON, SHUTDOWN, REBOOT, PASTE, SYNC;

	public static KeyboardEvent valueOf(int id) {
		for (KeyboardEvent event : values()) {
			if (event.ordinal() == id) {
				return event;
			}
		}
		return null;
	}
}