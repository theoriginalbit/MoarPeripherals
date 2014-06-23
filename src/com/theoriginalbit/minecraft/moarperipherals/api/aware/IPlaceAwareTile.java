package com.theoriginalbit.minecraft.moarperipherals.api.aware;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

public interface IPlaceAwareTile {
	public void onPlaced(EntityLivingBase entity, ItemStack stack, int x, int y, int z);
}
