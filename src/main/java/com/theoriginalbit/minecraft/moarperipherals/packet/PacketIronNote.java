package com.theoriginalbit.minecraft.moarperipherals.packet;

import com.theoriginalbit.minecraft.moarperipherals.MoarPeripherals;
import com.theoriginalbit.minecraft.moarperipherals.tile.TileIronNote;
import cpw.mods.fml.common.network.Player;
import net.minecraft.world.World;

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