package com.theoriginalbit.minecraft.moarperipherals.block;

import com.google.common.collect.Lists;
import com.theoriginalbit.minecraft.moarperipherals.interfaces.tile.IPairableDevice;
import com.theoriginalbit.minecraft.moarperipherals.utils.WorldUtils;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import java.util.ArrayList;

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
public abstract class BlockPairable extends BlockRotatable {

    public BlockPairable(int id, Material material, String blockName) {
        super(id, material, blockName);
    }

    @Override
    public ArrayList<ItemStack> getBlockDropped(World world, int x, int y, int z, int metadata, int fortune) {
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
