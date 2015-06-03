/**
 * Copyright 2014-2015 Joshua Asbury (@theoriginalbit)
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.moarperipherals.world.chunk;

import com.google.common.collect.Maps;
import com.moarperipherals.util.LogUtil;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeChunkManager;

import java.util.HashMap;
import java.util.List;

public class ChunkLoadingCallback implements ForgeChunkManager.LoadingCallback {
    private static final HashMap<IChunkLoader, ForgeChunkManager.Ticket> TICKET_LIST = Maps.newHashMap();

    private static boolean ticketLoad(ForgeChunkManager.Ticket ticket, World world) {
        final NBTTagCompound tag = ticket.getModData();
        int x = tag.getInteger("targetX");
        int y = tag.getInteger("targetY");
        int z = tag.getInteger("targetZ");

        TileEntity tile = world.getTileEntity(x, y, z);

        if (tile == null) {
            LogUtil.debug(String.format("No TileEntity at %d %d %d", x, y, z));
            return false;
        }

        if (!(tile instanceof IChunkLoader)) {
            LogUtil.warn(String.format("Served ticket for TileEntity at %d %d %d that is not one of mine", x, y, z));
            return false;
        }

        ForgeChunkManager.forceChunk(ticket, ((IChunkLoader) tile).getChunkCoord());

        TICKET_LIST.put((IChunkLoader) tile, ticket);

        return true;
    }

    public static ForgeChunkManager.Ticket obtain(IChunkLoader chunkLoader) {
        return TICKET_LIST.remove(chunkLoader);
    }

    @Override
    public void ticketsLoaded(List<ForgeChunkManager.Ticket> tickets, World world) {
        LogUtil.debug("Previous chunk loading tickets exist, loading...");
        for (ForgeChunkManager.Ticket ticket : tickets) {
            try {
                if (!ticketLoad(ticket, world)) {
                    LogUtil.warn("Served an invalid chunk loading ticket. Releasing.");
                    TicketManager.releaseTicket(ticket);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
