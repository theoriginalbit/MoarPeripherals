package com.theoriginalbit.minecraft.moarperipherals.handler;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import com.theoriginalbit.minecraft.moarperipherals.MoarPeripherals;
import com.theoriginalbit.minecraft.moarperipherals.tile.TileIronNote;
import com.theoriginalbit.minecraft.moarperipherals.utils.ComputerUtils;

import net.minecraft.network.NetServerHandler;
import net.minecraft.network.packet.NetHandler;
import net.minecraft.network.packet.Packet131MapData;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatAllowedCharacters;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.ITinyPacketHandler;

public class TinyPacketHandler implements ITinyPacketHandler {
	
	public enum PacketID {
		IRON_NOTE,
		KEYBOARD_ONKEY,
		KEYBOARD_TERMINATE,
		KEYBOARD_TURNON,
		KEYBOARD_SHUTDOWN,
		KEYBOARD_REBOOT,
		KEYBOARD_PASTE;
		
		public static PacketID valueOf(int id) {
			for (PacketID packet : values()) {
				if (packet.ordinal() == id) {
					return packet;
				}
			}
			return null;
		}
	}

	@Override
	public void handle(NetHandler handler, Packet131MapData mapData) {
		PacketID packetId = PacketID.valueOf(mapData.uniqueID);
		if (packetId != null) {
			ByteArrayDataInput stream = ByteStreams.newDataInput(mapData.itemData);
			
			switch (packetId) {
				case IRON_NOTE:
					handleIronNote(handler, stream);
					break;
				case KEYBOARD_ONKEY:
					handleKeyboardOnKey(handler, stream);
					break;
				case KEYBOARD_TERMINATE:
					handleKeyboardTerminate(handler, stream);
					break;
				case KEYBOARD_TURNON:
					handleKeyboardTurnOn(handler, stream);
					break;
				case KEYBOARD_SHUTDOWN:
					handleKeyboardShutdown(handler, stream);
					break;
				case KEYBOARD_REBOOT:
					handleKeyboardReboot(handler, stream);
					break;
				case KEYBOARD_PASTE:
					handleKeyboardPaste(handler, stream);
					break;
				default: break;
			}
		}
	}
	
	private final void handleIronNote(NetHandler handler, ByteArrayDataInput stream) {
		if (handler instanceof NetServerHandler) { return; }
		double x = stream.readDouble();
		double y = stream.readDouble();
		double z = stream.readDouble();
		int instrument = stream.readUnsignedByte();
		int pitch = stream.readUnsignedByte();
		int dimId = stream.readUnsignedByte();
		
		TileIronNote.play(MoarPeripherals.proxy.getClientWorld(dimId), x, y, z, instrument, pitch);
	}
	
	private final void handleKeyboardOnKey(NetHandler handler, ByteArrayDataInput stream) {
		char ch = stream.readChar();
		int keyCode = stream.readInt();
		int dimId = stream.readInt();
		int xPos = stream.readInt();
		int yPos = stream.readInt();
		int zPos = stream.readInt();
		
		World world = MoarPeripherals.proxy.getClientWorld(dimId);
		TileEntity tile = ComputerUtils.getTileComputerBase(world, xPos, yPos, zPos);
		if (tile != null) {
			ComputerUtils.queueEvent(tile, "key", keyCode);
			if (ChatAllowedCharacters.isAllowedCharacter(ch)) {
				ComputerUtils.queueEvent(tile, "char", Character.toString(ch));
			}
		}
	}
	
	private final void handleKeyboardTerminate(NetHandler handler, ByteArrayDataInput stream) {
		int dimId = stream.readInt();
		int xPos = stream.readInt();
		int yPos = stream.readInt();
		int zPos = stream.readInt();
		
		World world = MoarPeripherals.proxy.getClientWorld(dimId);
		TileEntity tile = ComputerUtils.getTileComputerBase(world, xPos, yPos, zPos);
		if (tile != null) {
			ComputerUtils.terminate(tile);
		}
	}
	
	private final void handleKeyboardTurnOn(NetHandler handler, ByteArrayDataInput stream) {
		int dimId = stream.readInt();
		int xPos = stream.readInt();
		int yPos = stream.readInt();
		int zPos = stream.readInt();
		
		World world = MoarPeripherals.proxy.getClientWorld(dimId);
		TileEntity tile = ComputerUtils.getTileComputerBase(world, xPos, yPos, zPos);
		if (tile != null) {
			if (!ComputerUtils.isOn(tile)) {
				ComputerUtils.turnOn(tile);
			}
		}
	}
	
	private final void handleKeyboardShutdown(NetHandler handler, ByteArrayDataInput stream) {
		int dimId = stream.readInt();
		int xPos = stream.readInt();
		int yPos = stream.readInt();
		int zPos = stream.readInt();
		
		World world = MoarPeripherals.proxy.getClientWorld(dimId);
		TileEntity tile = ComputerUtils.getTileComputerBase(world, xPos, yPos, zPos);
		if (tile != null) {
			if (ComputerUtils.isOn(tile)) {
				ComputerUtils.shutdown(tile);
			}
		}
	}
	
	private final void handleKeyboardReboot(NetHandler handler, ByteArrayDataInput stream) {
		int dimId = stream.readInt();
		int xPos = stream.readInt();
		int yPos = stream.readInt();
		int zPos = stream.readInt();
		
		World world = MoarPeripherals.proxy.getClientWorld(dimId);
		TileEntity tile = ComputerUtils.getTileComputerBase(world, xPos, yPos, zPos);
		if (tile != null) {
			ComputerUtils.reboot(tile);
		}
	}
	
	private void handleKeyboardPaste(NetHandler handler, ByteArrayDataInput stream) {
		String clipboard = stream.readUTF();
		int dimId = stream.readInt();
		int xPos = stream.readInt();
		int yPos = stream.readInt();
		int zPos = stream.readInt();
		
		World world = MoarPeripherals.proxy.getClientWorld(dimId);
		TileEntity tile = ComputerUtils.getTileComputerBase(world, xPos, yPos, zPos);
		if (tile != null) {
			ComputerUtils.paste(tile, clipboard);
		}
	}
}