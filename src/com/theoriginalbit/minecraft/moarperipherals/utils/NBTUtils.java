package com.theoriginalbit.minecraft.moarperipherals.utils;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public final class NBTUtils {
	public static NBTTagCompound getItemTag(ItemStack stack) {
		if (stack.stackTagCompound == null) {
			stack.stackTagCompound = new NBTTagCompound("tag");
		}
		return stack.stackTagCompound;
	}
}
