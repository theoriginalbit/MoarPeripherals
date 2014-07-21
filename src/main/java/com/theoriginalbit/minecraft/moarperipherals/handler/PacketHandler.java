package com.theoriginalbit.minecraft.moarperipherals.handler;

import com.google.common.collect.Sets;
import com.theoriginalbit.minecraft.moarperipherals.packet.PacketGeneric;
import com.theoriginalbit.minecraft.moarperipherals.packet.PacketIronNote;
import com.theoriginalbit.minecraft.moarperipherals.packet.PacketType;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.server.management.PlayerManager;
import net.minecraft.world.WorldServer;

import java.util.Set;

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
public class PacketHandler implements IPacketHandler {

    public static Set<EntityPlayer> getPlayersWatchingChunk(WorldServer world, int chunkX, int chunkZ) {
        PlayerManager manager = world.getPlayerManager();

        Set<EntityPlayer> playerList = Sets.newHashSet();
        for (Object o : world.playerEntities) {
            EntityPlayerMP player = (EntityPlayerMP) o;
            if (manager.isPlayerWatchingChunk(player, chunkX, chunkZ)) playerList.add(player);
        }
        return playerList;
    }

    public static Set<EntityPlayer> getPlayersWatchingBlock(WorldServer world, int blockX, int blockZ) {
        return getPlayersWatchingChunk(world, blockX >> 4, blockZ >> 4);
    }

    @Override
    public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player) {
        PacketType type = PacketType.valueOf(packet.data[0]);
        PacketGeneric mpPacket;

        switch (type) {
            case IRON_NOTE:
                mpPacket = new PacketIronNote();
                break;
            default:
                return;
        }

        try {
            mpPacket.handlePacket(packet.data, player);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}