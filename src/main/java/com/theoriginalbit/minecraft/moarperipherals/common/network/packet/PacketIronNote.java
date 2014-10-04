/**
 * Copyright (c) 2013-2014, Joshua Asbury (@theoriginalbit)
 * http://wiki.theoriginalbit.com/moarperipherals/
 *
 * MoarPeripherals is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://www.mod-buildcraft.com/MMPL-1.0.txt
 */
package com.theoriginalbit.minecraft.moarperipherals.common.network.packet;

import com.theoriginalbit.minecraft.moarperipherals.MoarPeripherals;
import com.theoriginalbit.minecraft.moarperipherals.common.tile.TileIronNote;
import cpw.mods.fml.common.network.Player;
import net.minecraft.world.World;

public class PacketIronNote extends PacketGeneric {

    public PacketIronNote() {
        super(PacketType.IRON_NOTE.ordinal());
    }

    @Override
    public void handlePacket(byte[] bytes, Player player) throws Exception {
        super.handlePacket(bytes, player);
        World world = MoarPeripherals.proxy.getClientWorld(intData[0]);
        TileIronNote.play(world, intData[1], intData[2], intData[3], intData[4], intData[5]);
    }

}