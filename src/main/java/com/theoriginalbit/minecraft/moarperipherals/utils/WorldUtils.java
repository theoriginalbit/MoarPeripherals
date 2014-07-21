package com.theoriginalbit.minecraft.moarperipherals.utils;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
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
public final class WorldUtils {

    @SuppressWarnings("unchecked")
    public static <T> T getTileEntity(IBlockAccess world, int x, int y, int z, Class<T> te) {
        TileEntity tile = world.getBlockTileEntity(x, y, z);
        if (tile != null && te.isAssignableFrom(tile.getClass())) {
            return (T) tile;
        }
        return null;
    }

    public static boolean isClient(World world) {
        return world.isRemote;
    }

    public static boolean isServer(World world) {
        return !world.isRemote;
    }
}
