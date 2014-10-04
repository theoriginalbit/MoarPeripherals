/**
 * Copyright (c) 2013-2014, Joshua Asbury (@theoriginalbit)
 * http://wiki.theoriginalbit.com/moarperipherals/
 *
 * MoarPeripherals is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://www.mod-buildcraft.com/MMPL-1.0.txt
 */
package com.theoriginalbit.minecraft.moarperipherals.common.upgrades;

import com.theoriginalbit.minecraft.framework.peripheral.wrapper.PeripheralWrapper;
import com.theoriginalbit.minecraft.moarperipherals.common.config.ConfigHandler;
import com.theoriginalbit.minecraft.moarperipherals.common.reference.Constants;
import com.theoriginalbit.minecraft.moarperipherals.common.registry.ModBlocks;
import com.theoriginalbit.minecraft.moarperipherals.common.tile.TileDictionary;
import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.api.turtle.*;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;

public class UpgradeDictionary implements ITurtleUpgrade {

    @Override
    public int getUpgradeID() {
        return ConfigHandler.upgradeIdDictionary;
    }

    @Override
    public String getAdjective() {
        return Constants.UPGRADE.DICTIONARY.getLocalised();
    }

    @Override
    public TurtleUpgradeType getType() {
        return TurtleUpgradeType.Peripheral;
    }

    @Override
    public ItemStack getCraftingItem() {
        return new ItemStack(ModBlocks.blockDictionary);
    }

    @Override
    public IPeripheral createPeripheral(ITurtleAccess turtle, TurtleSide side) {
        return new PeripheralWrapper(new TileDictionary());
    }

    @Override
    public TurtleCommandResult useTool(ITurtleAccess turtle, TurtleSide side, TurtleVerb verb, int direction) {
        return TurtleCommandResult.failure();
    }

    @Override
    public Icon getIcon(ITurtleAccess turtle, TurtleSide side) {
        return ModBlocks.blockDictionary.getIcon(0, 0);
    }

    @Override
    public void update(ITurtleAccess turtle, TurtleSide side) {}

}
