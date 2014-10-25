/**
 * Copyright (c) 2013-2014, Joshua Asbury (@theoriginalbit)
 * http://wiki.theoriginalbit.com/moarperipherals/
 *
 * MoarPeripherals is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://www.mod-buildcraft.com/MMPL-1.0.txt
 */
package com.theoriginalbit.moarperipherals.common.upgrade;

import com.theoriginalbit.moarperipherals.common.config.ConfigHandler;
import com.theoriginalbit.moarperipherals.common.reference.Constants;
import com.theoriginalbit.moarperipherals.common.registry.ModBlocks;
import com.theoriginalbit.moarperipherals.common.tile.TileDictionary;
import com.theoriginalbit.moarperipherals.common.upgrade.abstracts.UpgradePeripheral;
import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.api.turtle.ITurtleAccess;
import net.minecraft.item.ItemStack;

public class UpgradeDictionary extends UpgradePeripheral {

    public UpgradeDictionary() {
        super(ConfigHandler.upgradeIdDictionary, Constants.UPGRADE.DICTIONARY, new ItemStack(ModBlocks.blockDictionary), TileDictionary.class);
    }

    @Override
    protected void update(ITurtleAccess turtle, IPeripheral peripheral) {
        // NO-OP
    }

}
