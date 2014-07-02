package com.theoriginalbit.minecraft.moarperipherals.interfaces.aware;

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
	public void onNeighbourChanged(int blockId);
}