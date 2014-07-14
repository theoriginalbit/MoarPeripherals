package com.theoriginalbit.minecraft.moarperipherals.packet;

import cpw.mods.fml.common.network.Player;

public class PacketKeyboard extends PacketGeneric {
	public PacketKeyboard() {
		super(PacketType.KEYBOARD.ordinal());
	}

	public PacketKeyboard(KeyboardEvent type) {
		super(PacketType.KEYBOARD.ordinal(), type.ordinal());
	}

	public void handlePacket(byte[] bytes, Player player) throws Exception {
		super.handlePacket(bytes, player);
		KeyboardEvent type = KeyboardEvent.valueOf(subPacketType);
		switch (type) {
		case SYNC_CLIENT:
			break;
		case SYNC_SERVER:
			break;
		default: throw new Exception("Invalid Keyboard packet, sub-packet type was " + subPacketType);
		}
	}
	
	public enum KeyboardEvent {
		SYNC_SERVER, SYNC_CLIENT;

		public static KeyboardEvent valueOf(int id) {
			for (KeyboardEvent event : values()) {
				if (event.ordinal() == id) {
					return event;
				}
			}
			return null;
		}
	}
}