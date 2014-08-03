package com.theoriginalbit.minecraft.moarperipherals.chunk;

import com.google.common.collect.Maps;
import com.theoriginalbit.minecraft.moarperipherals.interfaces.IChunkLoader;
import com.theoriginalbit.minecraft.moarperipherals.utils.LogUtils;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeChunkManager;

import java.util.HashMap;
import java.util.List;

/**
 * A Minecraft mod that adds more peripherals into the ComputerCraft mod.
 * Official Thread:
 * http://www.computercraft.info/forums2/index.php?/topic/19357-
 * Official Wiki:
 * http://wiki.theoriginalbit.com/moarperipherals/
 * <p/>
 * Copyright (C) 2014  Joshua Asbury (@theoriginalbit)
 * <p/>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p/>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p/>
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/.
 */
public class ChunkLoadingCallback implements ForgeChunkManager.LoadingCallback {

    public static final HashMap<IChunkLoader, ForgeChunkManager.Ticket> ticketList = Maps.newHashMap();

    @Override
    public void ticketsLoaded(List<ForgeChunkManager.Ticket> tickets, World world) {
        LogUtils.info("Previous chunk loading tickets exist, loading...");
        for (ForgeChunkManager.Ticket ticket : tickets) {
            try {
                if (!ticketLoad(ticket, world)) {
                    LogUtils.warning("Invalid chunk ticket. Releasing.");
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

        TileEntity tile = world.getBlockTileEntity(x, y, z);

        if (tile == null) {
            LogUtils.warning(String.format("No TileEntity at %d %d %d", x, y, z));
            return false;
        }

        if (!(tile instanceof IChunkLoader)) {
            LogUtils.warning(String.format("Ticket exists for TileEntity at %d %d %d that is not one of mine", x, y, z));
            return false;
        }

        ForgeChunkManager.forceChunk(ticket, ((IChunkLoader) tile).getChunkCoord());

        ticketList.put((IChunkLoader) tile, ticket);

        return true;
    }

}
