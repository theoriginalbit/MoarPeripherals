package com.theoriginalbit.minecraft.moarperipherals.interfaces;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public interface ITooltipInformer {
	@SideOnly(Side.CLIENT)
	public void addInformativeTooltip(ItemStack stack, EntityPlayer player, List<String> list, boolean bool);
}