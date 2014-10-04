/**
 * Copyright (c) 2013-2014, Joshua Asbury (@theoriginalbit)
 * http://wiki.theoriginalbit.com/moarperipherals/
 *
 * MoarPeripherals is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://www.mod-buildcraft.com/MMPL-1.0.txt
 */
package com.theoriginalbit.minecraft.moarperipherals.api.tile.aware;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

/**
 * Subscribe your {@link TileEntity} to block right-clicks
 *
 * @author theoriginalbit
 */
public interface IPlaceAwareTile {
    /**
     * Invoked when the block is placed
     */
    public void onPlaced(EntityLivingBase entity, ItemStack stack, int x, int y, int z);

}