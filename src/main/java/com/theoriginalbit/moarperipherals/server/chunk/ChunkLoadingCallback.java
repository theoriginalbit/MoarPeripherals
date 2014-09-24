/**
 * Copyright (c) 2013-2014, Joshua Asbury (@theoriginalbit)
 * http://wiki.theoriginalbit.com/moarperipherals/
 *
 * MoarPeripherals is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://www.mod-buildcraft.com/MMPL-1.0.txt
 */
package com.theoriginalbit.moarperipherals.server.chunk;

import com.google.common.collect.Maps;
import com.theoriginalbit.moarperipherals.utils.LogUtils;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeChunkManager;

import java.util.HashMap;
import java.util.List;

public class ChunkLoadingCallback implements ForgeChunkManager.LoadingCallback {

    public static final HashMap<IChunkLoader, ForgeChunkManager.Ticket> ticketList = Maps.newHashMap();

    @Override
    public void ticketsLoaded(List<ForgeChunkManager.Ticket> tickets, World world) {
        LogUtils.debug("Previous chunk loading tickets exist, loading...");
        for (ForgeChunkManager.Ticket ticket : tickets) {
            try {
                if (!ticketLoad(ticket, world)) {
                    LogUtils.warn("Served an invalid chunk loading ticket. Releasing.");
                    TicketManager.releaseTicket(ticket);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static boolean ticketLoad(ForgeChunkManager.Ticket ticket, World world) {
        final NBTTagCompound tag = ticket.getModData();
        int x = tag.getInteger("targetX");
        int y = tag.getInteger("targetY");
        int z = tag.getInteger("targetZ");

        TileEntity tile = world.getTileEntity(x, y, z);

        if (tile == null) {
            LogUtils.debug(String.format("No TileEntity at %d %d %d", x, y, z));
            return false;
        }

        if (!(tile instanceof IChunkLoader)) {
            LogUtils.warn(String.format("Served ticket for TileEntity at %d %d %d that is not one of mine", x, y, z));
            return false;
        }

        ForgeChunkManager.forceChunk(ticket, ((IChunkLoader) tile).getChunkCoord());

        ticketList.put((IChunkLoader) tile, ticket);

        return true;
    }

}
