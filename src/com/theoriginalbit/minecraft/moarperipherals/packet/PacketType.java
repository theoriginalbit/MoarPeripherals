package com.theoriginalbit.minecraft.moarperipherals.packet;

public enum PacketType {
	IRON_NOTE,
	KEYBOARD;
	
	public static PacketType valueOf(int id) {
		for (PacketType type : values()) {
			if (type.ordinal() == id) {
				return type;
			}
		}
		return null;
	}
}