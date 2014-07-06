package com.theoriginalbit.minecraft.moarperipherals;

import com.theoriginalbit.minecraft.moarperipherals.reference.Settings;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class CreativeTabMoarPeripheral extends CreativeTabs {
	public CreativeTabMoarPeripheral() {
		super("tabMoarPeripherals");
	}

	@Override
	public int getTabIconItemIndex() {
		if (Settings.enableChatBox) {
			return Settings.blockChatBoxID;
		} else if (Settings.enablePlayerDetector) {
			return Settings.blockPlayerDetectorID;
		}
		return Item.skull.itemID;
	}
}
