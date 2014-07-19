package com.theoriginalbit.minecraft.moarperipherals.utils;

import com.theoriginalbit.minecraft.moarperipherals.packet.PacketGeneric;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import net.minecraft.entity.player.EntityPlayer;

public final class PacketUtils {

    public static void sendToServer(PacketGeneric packet) {
        PacketDispatcher.sendPacketToServer(packet.toPacket());
    }

    public static void sendToAllPlayers(PacketGeneric packet) {
        PacketDispatcher.sendPacketToAllPlayers(packet.toPacket());
    }

    public static void sendToPlayer(PacketGeneric packet, EntityPlayer player) {
        PacketDispatcher.sendPacketToPlayer(packet.toPacket(), (Player) player);
    }

    public static void sendToPlayersAround(PacketGeneric packet, double x, double y, double z, double range, int dimId) {
        PacketDispatcher.sendPacketToAllAround(x, y, z, range, dimId, packet.toPacket());
    }

    public static void sendToPlayersInDimension(PacketGeneric packet, int dimId) {
        PacketDispatcher.sendPacketToAllInDimension(packet.toPacket(), dimId);
    }

}