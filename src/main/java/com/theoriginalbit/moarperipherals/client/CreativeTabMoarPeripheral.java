/**
 * Copyright 2014 Joshua Asbury (@theoriginalbit)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.theoriginalbit.moarperipherals.client;

import com.theoriginalbit.moarperipherals.common.config.ConfigHandler;
import com.theoriginalbit.moarperipherals.common.reference.ComputerCraftInfo;
import com.theoriginalbit.moarperipherals.common.registry.UpgradeRegistry;
import dan200.computercraft.api.turtle.ITurtleUpgrade;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.List;

public class CreativeTabMoarPeripheral extends CreativeTabs {
    private static final String UPGRADE_LEFT = "leftUpgrade";

    public CreativeTabMoarPeripheral() {
        super("tabMoarPeripherals");
    }

    @Override
    public int getTabIconItemIndex() {
        if (ConfigHandler.enableAntenna) {
            return ConfigHandler.blockIdAntenna;
        } else if (ConfigHandler.enablePlayerDetector) {
            return ConfigHandler.blockIdPlayerDetector;
        }
        return Item.skull.itemID;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void displayAllReleventItems(List list) {
        super.displayAllReleventItems(list);
        // add the upgrades to the Normal Turtles
        for (final ITurtleUpgrade upgrade : UpgradeRegistry.UPGRADES) {
            final ItemStack normal = ComputerCraftInfo.cc_turtle.copy();
            normal.stackTagCompound = new NBTTagCompound();
            normal.stackTagCompound.setShort(UPGRADE_LEFT, (short) upgrade.getUpgradeID());
            list.add(normal);
        }
        // add the upgrades to the Advanced Turtles
        for (final ITurtleUpgrade upgrade : UpgradeRegistry.UPGRADES) {
            final ItemStack advanced = ComputerCraftInfo.cc_turtle_adv.copy();
            advanced.stackTagCompound = new NBTTagCompound();
            advanced.stackTagCompound.setShort(UPGRADE_LEFT, (short) upgrade.getUpgradeID());
            list.add(advanced);
        }
    }

}