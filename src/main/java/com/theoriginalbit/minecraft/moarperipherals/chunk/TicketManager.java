package com.theoriginalbit.minecraft.moarperipherals.chunk;

import com.google.common.collect.Sets;
import com.theoriginalbit.minecraft.moarperipherals.MoarPeripherals;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeChunkManager;

import java.util.HashSet;

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
public final class TicketManager {

    public static HashSet<ForgeChunkManager.Ticket> tickets = Sets.newHashSet();

    public static ForgeChunkManager.Ticket requestTicket(World world, int x, int y, int z) {
        ForgeChunkManager.Ticket ticket = null;
        if (!world.isRemote) {
            ticket = ForgeChunkManager.requestTicket(MoarPeripherals.instance, world, ForgeChunkManager.Type.NORMAL);
            if (ticket != null) {
                tickets.add(ticket);
            }
            NBTTagCompound tag = ticket.getModData();
            tag.setInteger("targetX", x);
            tag.setInteger("targetY", y);
            tag.setInteger("targetZ", z);
        }
        return ticket;
    }

    public static void releaseTicket(ForgeChunkManager.Ticket ticket) {
        if (tickets.contains(ticket)) {
            tickets.remove(ticket);
        }
        ForgeChunkManager.releaseTicket(ticket);
    }

}
