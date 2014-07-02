package com.theoriginalbit.minecraft.moarperipherals.interfaces.aware;

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
	public boolean onActivated(EntityPlayer player, int side, float hitX,
			float hitY, float hitZ);
}