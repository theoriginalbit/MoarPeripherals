/**
 * Copyright (c) 2013-2014, Joshua Asbury (@theoriginalbit)
 * http://wiki.theoriginalbit.com/moarperipherals/
 *
 * MoarPeripherals is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://www.mod-buildcraft.com/MMPL-1.0.txt
 */
package com.theoriginalbit.moarperipherals.utils;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public final class WorldUtils {

    @SuppressWarnings("unchecked")
    public static <T> T getTileEntity(IBlockAccess world, int x, int y, int z, Class<T> te) {
        TileEntity tile = world.getTileEntity(x, y, z);
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
