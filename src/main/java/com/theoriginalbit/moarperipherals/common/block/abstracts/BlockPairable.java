/**
 * Copyright (c) 2013-2014, Joshua Asbury (@theoriginalbit)
 * http://wiki.theoriginalbit.com/moarperipherals/
 *
 * MoarPeripherals is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://www.mod-buildcraft.com/MMPL-1.0.txt
 */
package com.theoriginalbit.moarperipherals.common.block.abstracts;

import com.google.common.collect.Lists;
import com.theoriginalbit.moarperipherals.api.tile.IPairableDevice;
import com.theoriginalbit.moarperipherals.common.utils.WorldUtils;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import java.util.ArrayList;

public abstract class BlockPairable extends BlockRotatable {

    public BlockPairable(Material material, String blockName) {
        super(material, blockName);
    }

    @Override
    public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
        return Lists.newArrayList();
    }

    @Override
    public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z) {
        ItemStack stack = createPairedItemStack(world, x, y, z);
        return stack != null ? stack : super.getPickBlock(target, world, x, y, z);
    }

    private ItemStack createPairedItemStack(World world, int x, int y, int z) {
        IPairableDevice device = WorldUtils.getTileEntity(world, x, y, z, IPairableDevice.class);
        if (device != null) {
            return device.getPairedDrop();
        }
        return null;
    }

}
