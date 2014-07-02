package com.theoriginalbit.minecraft.moarperipherals.interfaces.aware;

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