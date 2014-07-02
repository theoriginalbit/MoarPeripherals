package com.theoriginalbit.minecraft.moarperipherals.interfaces.aware;

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
	public void onPlaced(EntityLivingBase entity, ItemStack stack, int x,
			int y, int z);
}