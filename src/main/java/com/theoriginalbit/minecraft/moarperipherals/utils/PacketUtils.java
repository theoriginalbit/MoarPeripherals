package com.theoriginalbit.minecraft.moarperipherals.utils;

import com.theoriginalbit.minecraft.moarperipherals.packet.PacketGeneric;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import net.minecraft.entity.player.EntityPlayer;

/**
 * A Minecraft mod that adds more peripherals into the ComputerCraft mod.
 * Official Thread:
 * http://www.computercraft.info/forums2/index.php?/topic/19357-
 * Official Wiki:
 * http://wiki.theoriginalbit.com/moarperipherals/
 *
 * Copyright (C) 2014  Joshua Asbury (@theoriginalbit)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/.
 */
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