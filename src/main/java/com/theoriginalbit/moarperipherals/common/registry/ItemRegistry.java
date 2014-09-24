/**
 * Copyright (c) 2013-2014, Joshua Asbury (@theoriginalbit)
 * http://wiki.theoriginalbit.com/moarperipherals/
 *
 * MoarPeripherals is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://www.mod-buildcraft.com/MMPL-1.0.txt
 */
package com.theoriginalbit.moarperipherals.common.registry;

import com.theoriginalbit.moarperipherals.common.item.ItemMPBase;
import com.theoriginalbit.moarperipherals.common.item.ItemInkCartridge;
import com.theoriginalbit.moarperipherals.common.item.ItemSonic;
import com.theoriginalbit.moarperipherals.reference.Settings;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;

public class ItemRegistry {

    public static ItemMPBase itemInkCartridge;
    public static Item itemSonic;

    public static void init() {
        if (Settings.enablePrinter) {
            itemInkCartridge = new ItemInkCartridge();
            GameRegistry.registerItem(itemInkCartridge, itemInkCartridge.getUnlocalizedName());
        }

        if (Settings.isSonicEnabled()) {
            itemSonic = new ItemSonic();
            GameRegistry.registerItem(itemSonic, itemSonic.getUnlocalizedName());
        }
    }

    public static void oreRegistration() {

    }

}