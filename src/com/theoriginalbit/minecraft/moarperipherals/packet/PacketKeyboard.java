package com.theoriginalbit.minecraft.moarperipherals.packet;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatAllowedCharacters;
import net.minecraft.world.World;

import com.theoriginalbit.minecraft.moarperipherals.MoarPeripherals;
import com.theoriginalbit.minecraft.moarperipherals.keyboard.KeyboardEvent;
import com.theoriginalbit.minecraft.moarperipherals.tile.TileKeyboard;
import com.theoriginalbit.minecraft.moarperipherals.utils.ComputerUtils;

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
		common();

		KeyboardEvent type = KeyboardEvent.valueOf(subPacketType);
		switch (type) {
		case SYNC: sync(); break;
		case PASTE: paste(); break;
		case REBOOT: reboot(); break;
		case TURN_ON: turnOn(); break;
		case SHUTDOWN: shutdown(); break;
		case KEY_PRESS: keyPress(); break;
		case TERMINATE: terminate(); break;
		default: throw new Exception("Invalid Keyboard packet, sub-packet type was " + subPacketType);
		}
	}

	private final String EVENT_KEY = "key";
	private final String EVENT_CHAR = "char";
	private TileEntity tile;

	private final void common() {
		World world = MoarPeripherals.proxy.getClientWorld(intData[0]);
		tile = ComputerUtils.getTileComputerBase(world, intData[1], intData[2], intData[3]);
	}

	private final void sync() {
		World world = MoarPeripherals.proxy.getClientWorld(intData[0]);
		TileEntity tile = world.getBlockTileEntity(intData[1], intData[2], intData[3]);
		if (tile != null) {
			((TileKeyboard) tile).sync(intData[4], intData[5], intData[6]);
		}
	}

	private final void paste() {
		if (tile != null) {
			ComputerUtils.paste(tile, stringData[0]);
		}
	}

	private final void reboot() {
		if (tile != null) {
			ComputerUtils.reboot(tile);
		}
	}

	private final void turnOn() {
		if (tile != null && !ComputerUtils.isOn(tile)) {
			ComputerUtils.turnOn(tile);
		}
	}

	private final void shutdown() {
		if (tile != null && ComputerUtils.isOn(tile)) {
			ComputerUtils.shutdown(tile);
		}
	}

	private final void keyPress() {
		if (tile != null) {
			ComputerUtils.queueEvent(tile, EVENT_KEY, intData[4]);
			char ch = charData[0];
			if (ChatAllowedCharacters.isAllowedCharacter(ch)) {
				ComputerUtils.queueEvent(tile, EVENT_CHAR, Character.toString(ch));
			}
		}
	}

	private final void terminate() {
		if (tile != null) {
			ComputerUtils.terminate(tile);
		}
	}
}