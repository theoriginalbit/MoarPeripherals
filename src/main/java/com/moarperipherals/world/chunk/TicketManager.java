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

import com.google.common.collect.Sets;
import com.moarperipherals.MoarPeripherals;
import com.moarperipherals.util.LogUtil;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeChunkManager;

import java.util.HashSet;

public final class TicketManager {

    public static HashSet<ForgeChunkManager.Ticket> tickets = Sets.newHashSet();

    public static ForgeChunkManager.Ticket requestTicket(World world, int x, int y, int z) {
        ForgeChunkManager.Ticket ticket = null;
        if (!world.isRemote) {
            LogUtil.debug(String.format("Requesting chunk loading ticket for TileEntity at %d %d %d", x, y, z));
            ticket = ForgeChunkManager.requestTicket(MoarPeripherals.INSTANCE, world, ForgeChunkManager.Type.NORMAL);
            if (ticket != null) {
                tickets.add(ticket);
                NBTTagCompound tag = ticket.getModData();
                tag.setInteger("targetX", x);
                tag.setInteger("targetY", y);
                tag.setInteger("targetZ", z);
            }
        }
        return ticket;
    }

    public static void releaseTicket(ForgeChunkManager.Ticket ticket) {
        if (tickets.contains(ticket)) {
            LogUtil.debug("Releasing chunk loading ticket.");
            tickets.remove(ticket);
        }
        ForgeChunkManager.releaseTicket(ticket);
    }

}
