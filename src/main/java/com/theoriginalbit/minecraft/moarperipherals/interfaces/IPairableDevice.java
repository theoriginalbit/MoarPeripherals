package com.theoriginalbit.minecraft.moarperipherals.interfaces;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public interface IPairableDevice {

    /**
     * Setup the target information based on NBT data and
     * return success.
     */
    public boolean configureTargetFromNbt(NBTTagCompound tag);

    public ItemStack getPairedDrop();
}
