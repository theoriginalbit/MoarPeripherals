/**
 * Copyright (c) 2013-2014, Joshua Asbury (@theoriginalbit)
 * http://wiki.theoriginalbit.com/moarperipherals/
 *
 * MoarPeripherals is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://www.mod-buildcraft.com/MMPL-1.0.txt
 */
package com.theoriginalbit.moarperipherals.common.upgrades;

import com.google.common.collect.ImmutableList;
import com.theoriginalbit.moarperipherals.common.config.ConfigHandler;
import com.theoriginalbit.moarperipherals.common.reference.Constants;
import com.theoriginalbit.moarperipherals.common.upgrades.abstracts.UpgradeTool;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

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
    protected boolean canHarvestBlock(World world, int x, int y, int z) {
        final Block block = world.getBlock(x, y, z);
        return SHEAR_DIG.contains(block);
    }

}
