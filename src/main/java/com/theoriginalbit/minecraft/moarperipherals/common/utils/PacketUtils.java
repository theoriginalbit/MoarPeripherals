/**
 * Copyright (c) 2013-2014, Joshua Asbury (@theoriginalbit)
 * http://wiki.theoriginalbit.com/moarperipherals/
 *
 * MoarPeripherals is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://www.mod-buildcraft.com/MMPL-1.0.txt
 */
package com.theoriginalbit.minecraft.moarperipherals.common.utils;

import com.theoriginalbit.minecraft.moarperipherals.common.network.packet.PacketGeneric;
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