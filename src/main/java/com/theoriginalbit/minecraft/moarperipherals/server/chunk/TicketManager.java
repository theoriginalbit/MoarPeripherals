/**
 * Copyright (c) 2013-2014, Joshua Asbury (@theoriginalbit)
 * http://wiki.theoriginalbit.com/moarperipherals/
 *
 * MoarPeripherals is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://www.mod-buildcraft.com/MMPL-1.0.txt
 */
package com.theoriginalbit.minecraft.moarperipherals.server.chunk;

import com.google.common.collect.Sets;
import com.theoriginalbit.minecraft.moarperipherals.MoarPeripherals;
import com.theoriginalbit.minecraft.moarperipherals.common.utils.LogUtils;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeChunkManager;

import java.util.HashSet;

public final class TicketManager {

    public static HashSet<ForgeChunkManager.Ticket> tickets = Sets.newHashSet();

    public static ForgeChunkManager.Ticket requestTicket(World world, int x, int y, int z) {
        ForgeChunkManager.Ticket ticket = null;
        if (!world.isRemote) {
            LogUtils.debug("Requesting chunk loading ticket for TileEntity at %d %d %d", x, y, z);
            ticket = ForgeChunkManager.requestTicket(MoarPeripherals.instance, world, ForgeChunkManager.Type.NORMAL);
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
            LogUtils.debug("Releasing chunk loading ticket.");
            tickets.remove(ticket);
        }
        ForgeChunkManager.releaseTicket(ticket);
    }

}
