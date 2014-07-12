package com.theoriginalbit.minecraft.moarperipherals.packet;

import net.minecraft.world.World;

import com.theoriginalbit.minecraft.moarperipherals.MoarPeripherals;
import com.theoriginalbit.minecraft.moarperipherals.tile.TileIronNote;

import cpw.mods.fml.common.network.Player;

public class PacketIronNote extends PacketGeneric {
	public PacketIronNote() {
		super(PacketType.IRON_NOTE.ordinal());
	}

	@Override
	public void handlePacket(byte[] bytes, Player player) throws Exception {
		super.handlePacket(bytes, player);
		World world = MoarPeripherals.proxy.getClientWorld(intData[0]);
		TileIronNote.play(world, intData[1], intData[2], intData[3], intData[4], intData[5]);
	}
}