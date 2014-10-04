/**
 * Copyright (c) 2013-2014, Joshua Asbury (@theoriginalbit)
 * http://wiki.theoriginalbit.com/moarperipherals/
 *
 * MoarPeripherals is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://www.mod-buildcraft.com/MMPL-1.0.txt
 */
package com.theoriginalbit.moarperipherals.api.tile.aware;

import net.minecraft.tileentity.TileEntity;

/**
 * Subscribe your {@link TileEntity} to the neighbouring blocks updating
 *
 * @author theoriginalbit
 */
public interface INeighborAwareTile {
    /**
     * Invoked when a neighbouring block changes
     */
    public void onNeighbourChanged();

}