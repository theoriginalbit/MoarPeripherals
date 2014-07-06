package com.theoriginalbit.minecraft.moarperipherals.network;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import com.theoriginalbit.minecraft.moarperipherals.MoarPeripherals;
import com.theoriginalbit.minecraft.moarperipherals.tile.TileIronNote;

import net.minecraft.network.NetServerHandler;
import net.minecraft.network.packet.NetHandler;
import net.minecraft.network.packet.Packet131MapData;
import cpw.mods.fml.common.network.ITinyPacketHandler;

public class TinyPacketHandler implements ITinyPacketHandler {

	@Override
	public void handle(NetHandler handler, Packet131MapData mapData) {
		ByteArrayDataInput stream = ByteStreams.newDataInput(mapData.itemData);
		
		switch (mapData.uniqueID) {
		case 0:
			if (handler instanceof NetServerHandler) { return; }
			double x = stream.readDouble();
			double y = stream.readDouble();
			double z = stream.readDouble();
			int instrument = stream.readUnsignedByte();
			int pitch = stream.readUnsignedByte();
			int dimId = stream.readUnsignedByte();
			
			TileIronNote.play(MoarPeripherals.proxy.getClientWorld(dimId), x, y, z, instrument, pitch);
			
			break;
		}
	}
}