/**
 * Copyright (c) 2013-2014, Joshua Asbury (@theoriginalbit)
 * http://wiki.theoriginalbit.com/moarperipherals/
 *
 * MoarPeripherals is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://www.mod-buildcraft.com/MMPL-1.0.txt
 */
package com.theoriginalbit.minecraft.moarperipherals.api.tile.aware;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;

/**
 * Subscribe your {@link TileEntity} to its block right-clicks
 *
 * @author theoriginalbit
 */
public interface IActivateAwareTile {
    /**
     * Invoked when the player right-clicks, you must return whether you did
     * something with the click or not
     */
    public boolean onActivated(EntityPlayer player, int side, float hitX, float hitY, float hitZ);

}