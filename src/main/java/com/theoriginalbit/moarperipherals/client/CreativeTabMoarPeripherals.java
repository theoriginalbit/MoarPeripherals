/**
 * Copyright 2014-2015 Joshua Asbury (@theoriginalbit)
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.theoriginalbit.moarperipherals.client;

import com.theoriginalbit.moarperipherals.common.config.ConfigData;
import com.theoriginalbit.moarperipherals.common.init.ComputerCraft;
import com.theoriginalbit.moarperipherals.common.init.ModBlocks;
import com.theoriginalbit.moarperipherals.common.integration.init.UpgradeRegistry;
import dan200.computercraft.api.turtle.ITurtleUpgrade;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.List;

public class CreativeTabMoarPeripherals extends CreativeTabs {
    private static final String UPGRADE_LEFT = "leftUpgrade";

    public CreativeTabMoarPeripherals() {
        super("tabMoarPeripherals");
    }

    @Override
    public Item getTabIconItem() {
        final Block block;

        if (ConfigData.enableAntenna) {
            block = ModBlocks.blockAntennaController;
        } else if (ConfigData.enablePlayerDetector) {
            block = ModBlocks.blockPlayerDetector;
        } else {
            block = null;
        }

        return block == null ? Items.skull : Item.getItemFromBlock(block);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void displayAllReleventItems(List list) {
        super.displayAllReleventItems(list);
        // add the upgrades to the Normal Turtles
        for (final ITurtleUpgrade upgrade : UpgradeRegistry.UPGRADES) {
            final ItemStack normal = ComputerCraft.cc_turtle.copy();
            normal.stackTagCompound = new NBTTagCompound();
            normal.stackTagCompound.setShort(UPGRADE_LEFT, (short) upgrade.getUpgradeID());
            list.add(normal);
        }
        // add the upgrades to the Advanced Turtles
        for (final ITurtleUpgrade upgrade : UpgradeRegistry.UPGRADES) {
            final ItemStack advanced = ComputerCraft.cc_turtle_adv.copy();
            advanced.stackTagCompound = new NBTTagCompound();
            advanced.stackTagCompound.setShort(UPGRADE_LEFT, (short) upgrade.getUpgradeID());
            list.add(advanced);
        }
    }
}