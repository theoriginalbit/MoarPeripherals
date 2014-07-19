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