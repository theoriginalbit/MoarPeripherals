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
package com.theoriginalbit.moarperipherals.common.upgrade;

import com.google.common.collect.ImmutableList;
import com.theoriginalbit.moarperipherals.common.config.ConfigHandler;
import com.theoriginalbit.moarperipherals.common.reference.Constants;
import com.theoriginalbit.moarperipherals.common.upgrade.abstracts.UpgradeTool;
import dan200.computercraft.api.turtle.ITurtleAccess;
import dan200.computercraft.api.turtle.TurtleSide;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;

import java.util.ArrayList;

/**
 * @author theoriginalbit
 * @since 13/10/2014
 */
public class UpgradeShears extends UpgradeTool {
    private static final ImmutableList<Block> SHEAR_DIG = ImmutableList.of(Blocks.wool, Blocks.web);

    public UpgradeShears() {
        super(ConfigHandler.upgradeIdShears, Constants.UPGRADE.SHEARS, new ItemStack(Items.shears));
    }

    @Override
    public IIcon getIcon(ITurtleAccess turtle, TurtleSide side) {
        return Items.shears.getIconFromDamage(0);
    }

    @Override
    protected boolean canHarvestBlock(World world, int x, int y, int z) {
        final Block block = world.getBlock(x, y, z);
        return block instanceof IShearable || SHEAR_DIG.contains(block);
    }

    @Override
    protected ArrayList<ItemStack> harvestBlock(World world, int x, int y, int z) {
        final Block block = world.getBlock(x, y, z);
        final int metadata = world.getBlockMetadata(x, y, z);

        if (block instanceof IShearable) {
            return ((IShearable) block).onSheared(itemStack, world, x, y, z, 0);
        }

        return block.getDrops(world, x, y, z, metadata, 0);
    }
}
