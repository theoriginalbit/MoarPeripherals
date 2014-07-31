package com.theoriginalbit.minecraft.moarperipherals.upgrades;

import com.theoriginalbit.minecraft.framework.peripheral.wrapper.PeripheralWrapper;
import com.theoriginalbit.minecraft.moarperipherals.reference.Constants;
import com.theoriginalbit.minecraft.moarperipherals.reference.Settings;
import com.theoriginalbit.minecraft.moarperipherals.registry.BlockRegistry;
import com.theoriginalbit.minecraft.moarperipherals.tile.TileDictionary;
import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.api.turtle.*;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;

/**
 * A Minecraft mod that adds more peripherals into the ComputerCraft mod.
 * Official Thread:
 * http://www.computercraft.info/forums2/index.php?/topic/19357-
 * Official Wiki:
 * http://wiki.theoriginalbit.com/moarperipherals/
 *
 * Copyright (C) 2014  Joshua Asbury (@theoriginalbit)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/.
 */
public class UpgradeDictionary implements ITurtleUpgrade {

    @Override
    public int getUpgradeID() {
        return Settings.upgradeIdDictionary;
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
        return new ItemStack(BlockRegistry.blockDictionary);
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
        return BlockRegistry.blockDictionary.getIcon(0, 0);
    }

    @Override
    public void update(ITurtleAccess turtle, TurtleSide side) {}

}
