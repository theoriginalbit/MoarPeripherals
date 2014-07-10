package com.theoriginalbit.minecraft.moarperipherals.item;

import com.theoriginalbit.minecraft.moarperipherals.MoarPeripherals;
import com.theoriginalbit.minecraft.moarperipherals.reference.ModInfo;
import com.theoriginalbit.minecraft.moarperipherals.utils.NeiUtils;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.Item;

public class ItemGeneric extends Item {
	
	private final String name;

	public ItemGeneric(int itemId, String itemName) {
		super(itemId - 256);
		name = itemName;
		
		setUnlocalizedName(ModInfo.RESOURCE_DOMAIN + "." + name);
		setCreativeTab(MoarPeripherals.creativeTab);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister registry) {
		itemIcon = registry.registerIcon(ModInfo.RESOURCE_DOMAIN + ":" + name);
	}
	
	public ItemGeneric hideFromNEI() {
		NeiUtils.hideFromNEI(itemID);
		return this;
	}
}