package com.theoriginalbit.minecraft.moarperipherals.utils;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

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
