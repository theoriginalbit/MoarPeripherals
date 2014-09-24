/**
 * Copyright (c) 2013-2014, Joshua Asbury (@theoriginalbit)
 * http://wiki.theoriginalbit.com/moarperipherals/
 *
 * MoarPeripherals is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://www.mod-buildcraft.com/MMPL-1.0.txt
 */
package com.theoriginalbit.moarperipherals.common.handler;

import com.google.common.collect.Sets;
import com.theoriginalbit.moarperipherals.common.network.MessageType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.NetworkManager;
import net.minecraft.server.management.PlayerManager;
import net.minecraft.world.WorldServer;

import java.util.Set;

public class PacketHandler implements IPacketHandler {

    public static Set<EntityPlayer> getPlayersWatchingChunk(WorldServer world, int chunkX, int chunkZ) {
        final PlayerManager manager = world.getPlayerManager();

        final Set<EntityPlayer> playerList = Sets.newHashSet();
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
    public void onPacketData(NetworkManager manager, Packet250CustomPayload packet, Player player) {
        MessageType type = MessageType.valueOf(packet.data[0]);
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