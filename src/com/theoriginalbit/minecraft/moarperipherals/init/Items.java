package com.theoriginalbit.minecraft.moarperipherals.init;

import com.theoriginalbit.minecraft.moarperipherals.item.*;
import com.theoriginalbit.minecraft.moarperipherals.reference.Settings;

import cpw.mods.fml.common.registry.GameRegistry;

public class Items {
	public static ItemGeneric itemInkCartridgeEmpty;
	public static ItemGeneric itemInkCartridgeFilled;
	
	public static void init() {
		if (Settings.enablePrinter) {
			itemInkCartridgeEmpty = new ItemInkCartridgeEmpty();
			GameRegistry.registerItem(itemInkCartridgeEmpty, itemInkCartridgeEmpty.getUnlocalizedName());
			
			itemInkCartridgeFilled = new ItemInkCartridgeFilled();
			GameRegistry.registerItem(itemInkCartridgeEmpty, itemInkCartridgeFilled.getUnlocalizedName());
		}
	}
}