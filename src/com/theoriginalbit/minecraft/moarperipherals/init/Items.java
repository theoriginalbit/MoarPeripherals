package com.theoriginalbit.minecraft.moarperipherals.init;

import com.theoriginalbit.minecraft.moarperipherals.item.ItemGeneric;
import com.theoriginalbit.minecraft.moarperipherals.item.ItemInkCartridge;
import com.theoriginalbit.minecraft.moarperipherals.reference.Settings;

import cpw.mods.fml.common.registry.GameRegistry;

public class Items {
	public static ItemGeneric itemInkCartridge;
	
	public static void init() {		
		if (Settings.enablePrinter) {
			itemInkCartridge = new ItemInkCartridge();
			GameRegistry.registerItem(itemInkCartridge, itemInkCartridge.getUnlocalizedName());
		}
	}
}