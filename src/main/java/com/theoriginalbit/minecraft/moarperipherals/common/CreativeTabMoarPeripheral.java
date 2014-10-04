/**
 * Copyright (c) 2013-2014, Joshua Asbury (@theoriginalbit)
 * http://wiki.theoriginalbit.com/moarperipherals/
 *
 * MoarPeripherals is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://www.mod-buildcraft.com/MMPL-1.0.txt
 */
package com.theoriginalbit.minecraft.moarperipherals.common;

import com.theoriginalbit.minecraft.moarperipherals.common.config.ConfigHandler;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class CreativeTabMoarPeripheral extends CreativeTabs {

    public CreativeTabMoarPeripheral() {
        super("tabMoarPeripherals");
    }

    @Override
    public int getTabIconItemIndex() {
        if (ConfigHandler.enableChatBox) {
            return ConfigHandler.blockIdChatBox;
        } else if (ConfigHandler.enablePlayerDetector) {
            return ConfigHandler.blockIdPlayerDetector;
        }
        return Item.skull.itemID;
    }

}