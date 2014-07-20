package com.theoriginalbit.minecraft.moarperipherals.block;

import com.google.common.collect.Lists;
import com.theoriginalbit.minecraft.moarperipherals.interfaces.IPairableDevice;
import com.theoriginalbit.minecraft.moarperipherals.utils.WorldUtils;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import java.util.ArrayList;

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
