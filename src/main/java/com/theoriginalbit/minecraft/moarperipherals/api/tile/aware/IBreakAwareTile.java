/**
 * Copyright (c) 2013-2014, Joshua Asbury (@theoriginalbit)
 * http://wiki.theoriginalbit.com/moarperipherals/
 *
 * MoarPeripherals is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://www.mod-buildcraft.com/MMPL-1.0.txt
 */
package com.theoriginalbit.minecraft.moarperipherals.api.tile.aware;

import net.minecraft.tileentity.TileEntity;

/**
 * Subscribe your {@link TileEntity} to its block break
 *
 * @author theoriginalbit
 */
public interface IBreakAwareTile {
    /**
     * Invoked when the block is broken
     */
    public void onBreak(int x, int y, int z);

}