/**
 * Copyright (c) 2013-2014, Joshua Asbury (@theoriginalbit)
 * http://wiki.theoriginalbit.com/moarperipherals/
 *
 * MoarPeripherals is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://www.mod-buildcraft.com/MMPL-1.0.txt
 */
package com.theoriginalbit.moarperipherals.common;

import com.theoriginalbit.moarperipherals.common.registry.BlockRegistry;
import com.theoriginalbit.moarperipherals.reference.Settings;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

public class CreativeTabMoarPeripherals extends CreativeTabs {

    public CreativeTabMoarPeripherals() {
        super("tabMoarPeripherals");
    }

    @Override
    public Item getTabIconItem() {
        final Block block;

        if (Settings.enableChatBox) {
            block = BlockRegistry.blockChatBox;
        } else if (Settings.enablePlayerDetector) {
            block = BlockRegistry.blockPlayerDetector;
        } else {
            block = null;
        }

        return block == null ? Items.skull : Item.getItemFromBlock(block);
    }
}