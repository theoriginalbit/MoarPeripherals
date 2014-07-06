package com.theoriginalbit.minecraft.moarperipherals.item;

import java.util.List;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;

import com.theoriginalbit.minecraft.moarperipherals.reference.ModInfo;
import com.theoriginalbit.minecraft.moarperipherals.reference.Settings;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemInkCartridgeFilled extends ItemInkCartridge {
	private Icon[] icons;
	
	public ItemInkCartridgeFilled() {
		super(Settings.itemIdInkCartridgeFilled, "Filled");
		
		setMaxStackSize(1);
		setHasSubtypes(true);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister registry) {
		icons = new Icon[16];
		
		for (int i = 0; i < icons.length; ++i) {
			icons[i] = registry.registerIcon(ModInfo.RESOURCE_DOMAIN + ":inkCartridge" + i);
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIconFromDamage(int damage) {
		return icons[damage];
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void getSubItems(int itemId, CreativeTabs creativeTab, List itemList) {
		for (int i = 0; i < icons.length; ++i) {
			itemList.add(new ItemStack(this, 1, i));
		}
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		int damage = MathHelper.clamp_int(stack.getItemDamage(), 0, 15);
		return "item." + ModInfo.RESOURCE_DOMAIN + ".inkCartridge" + damage;
	}
}