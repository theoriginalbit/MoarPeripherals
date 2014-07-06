package com.theoriginalbit.minecraft.moarperipherals.item;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemInkCartridge extends ItemGeneric {
	
	public ItemInkCartridge(int id, String contents) {
		super(id, "inkCartridge" + contents);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void addInformation(ItemStack item, EntityPlayer player, List list, boolean bool) {
		list.add("Ink Level: " + EnumChatFormatting.GRAY + EnumChatFormatting.ITALIC + "0%");
	}
}