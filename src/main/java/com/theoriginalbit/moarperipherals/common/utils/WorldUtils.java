/**
 * Copyright (c) 2013-2014, Joshua Asbury (@theoriginalbit)
 * http://wiki.theoriginalbit.com/moarperipherals/
 *
 * MoarPeripherals is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://www.mod-buildcraft.com/MMPL-1.0.txt
 */
package com.theoriginalbit.moarperipherals.common.utils;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.List;

public final class WorldUtils {

    @SuppressWarnings("unchecked")
    public static <T> T getTileEntity(IBlockAccess world, int x, int y, int z, Class<T> te) {
        final TileEntity tile = world.getTileEntity(x, y, z);
        if (tile != null && te.isAssignableFrom(tile.getClass())) {
            return (T) tile;
        }
        return null;
    }

    public static ChunkCoordinates moveCoords(ChunkCoordinates coordinates, int dir) {
        return new ChunkCoordinates(coordinates.posX + Facing.offsetsXForSide[dir], coordinates.posY + Facing.offsetsYForSide[dir], coordinates.posZ + Facing.offsetsZForSide[dir]);
    }

    public static boolean isBlockInWorld(World world, ChunkCoordinates coordinates) {
        return coordinates.posY >= 0 && coordinates.posY < world.getHeight();
    }

    public static boolean isLiquidBlock(World world, ChunkCoordinates coordinates) {
        if (isBlockInWorld(world, coordinates)) {
            Block block = world.getBlock(coordinates.posX, coordinates.posY, coordinates.posZ);
            if (block != null) {
                return block.getMaterial().isLiquid();
            }
        }
        return false;
    }

    public static Entity rayTraceEntities(World world, Vec3 vecStart, Vec3 vecDir, double distance) {
        Vec3 vecEnd = vecStart.addVector(vecDir.xCoord * distance, vecDir.yCoord * distance, vecDir.zCoord * distance);

        MovingObjectPosition result = world.rayTraceBlocks(vecStart.addVector(0.0D, 0.0D, 0.0D), vecEnd.addVector(0.0D, 0.0D, 0.0D));
        if (result != null && result.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
            distance = vecStart.distanceTo(result.hitVec);
            vecEnd = vecStart.addVector(vecDir.xCoord * distance, vecDir.yCoord * distance, vecDir.zCoord * distance);
        }

        float xStretch = Math.abs(vecDir.xCoord) > 0.25D ? 0.0F : 1.0F;
        float yStretch = Math.abs(vecDir.yCoord) > 0.25D ? 0.0F : 1.0F;
        float zStretch = Math.abs(vecDir.zCoord) > 0.25D ? 0.0F : 1.0F;
        AxisAlignedBB bigBox = AxisAlignedBB.getBoundingBox(Math.min(vecStart.xCoord, vecEnd.xCoord) - 0.375F * xStretch, Math.min(vecStart.yCoord, vecEnd.yCoord) - 0.375F * yStretch, Math.min(vecStart.zCoord, vecEnd.zCoord) - 0.375F * zStretch, Math.max(vecStart.xCoord, vecEnd.xCoord) + 0.375F * xStretch, Math.max(vecStart.yCoord, vecEnd.yCoord) + 0.375F * yStretch, Math.max(vecStart.zCoord, vecEnd.zCoord) + 0.375F * zStretch);

        Entity closest = null;
        double closestDist = 99.0D;
        List list = world.getEntitiesWithinAABBExcludingEntity(null, bigBox);
        for (Object aList : list) {
            Entity entity = (Entity) aList;
            if (entity.canBeCollidedWith()) {
                AxisAlignedBB littleBox = entity.boundingBox;
                if (littleBox.isVecInside(vecStart)) {
                    closest = entity;
                    closestDist = 0.0D;
                } else {
                    MovingObjectPosition littleBoxResult = littleBox.calculateIntercept(vecStart, vecEnd);
                    if (littleBoxResult != null) {
                        double dist = vecStart.distanceTo(littleBoxResult.hitVec);
                        if ((closest == null) || (dist <= closestDist)) {
                            closest = entity;
                            closestDist = dist;
                        }
                    } else if (littleBox.intersectsWith(bigBox)) {
                        if (closest == null) {
                            closest = entity;
                            closestDist = distance;
                        }
                    }
                }
            }
        }
        if ((closest != null) && (closestDist <= distance)) {
            return closest;
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
