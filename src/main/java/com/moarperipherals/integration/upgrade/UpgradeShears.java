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
package com.moarperipherals.integration.upgrade;

import com.google.common.collect.ImmutableList;
import com.theoriginalbit.framework.peripheral.turtle.UpgradeTool;
import com.moarperipherals.config.ConfigData;
import com.moarperipherals.Constants;
import dan200.computercraft.api.turtle.ITurtleAccess;
import dan200.computercraft.api.turtle.TurtleSide;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
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
        super(ConfigData.upgradeIdShears, Constants.UPGRADE.SHEARS.getLocalised(), new ItemStack(Items.shears));
    }

    @Override
    public IIcon getIcon(ITurtleAccess turtle, TurtleSide side) {
        return Items.shears.getIconFromDamage(0);
    }

    @Override
    protected boolean canAttackEntity(Entity entity) {
        return entity instanceof IShearable && ((IShearable) entity).isShearable(craftingStack.copy(), entity.worldObj, (int) entity.posX, (int) entity.posY, (int) entity.posZ);
    }

    @Override
    protected ArrayList<ItemStack> attackEntity(ITurtleAccess turtle, Entity entity) {
        return ((IShearable) entity).onSheared(craftingStack.copy(), entity.worldObj, (int) entity.posX, (int) entity.posY, (int) entity.posZ, 0);
    }

    @Override
    protected boolean canAttackBlock(World world, int x, int y, int z, int dir, EntityPlayer turtle) {
        return false;
    }

    @Override
    protected ArrayList<ItemStack> attackBlock(World world, int x, int y, int z, int dir, EntityPlayer turtle) {
        return null;
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
            return ((IShearable) block).onSheared(craftingStack, world, x, y, z, 0);
        }

        return block.getDrops(world, x, y, z, metadata, 0);
    }
}
