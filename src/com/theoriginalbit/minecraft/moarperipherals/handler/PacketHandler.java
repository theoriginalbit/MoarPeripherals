package com.theoriginalbit.minecraft.moarperipherals.handler;

import com.theoriginalbit.minecraft.moarperipherals.packet.PacketGeneric;
import com.theoriginalbit.minecraft.moarperipherals.packet.PacketIronNote;
import com.theoriginalbit.minecraft.moarperipherals.packet.PacketKeyboard;
import com.theoriginalbit.minecraft.moarperipherals.packet.PacketType;

import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;

public class PacketHandler implements IPacketHandler {
	@Override
	public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player) {
		PacketType type = PacketType.valueOf(packet.data[0]);
		PacketGeneric mpPacket;
		
		switch (type) {
			case IRON_NOTE:
				mpPacket = new PacketIronNote();
				break;
			case KEYBOARD:
				mpPacket = new PacketKeyboard();
				break;
		default: return;
		}
		
		try {
			mpPacket.handlePacket(packet.data, player);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}