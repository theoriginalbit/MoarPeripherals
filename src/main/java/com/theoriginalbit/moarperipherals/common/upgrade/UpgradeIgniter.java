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

import com.theoriginalbit.moarperipherals.common.config.ConfigHandler;
import com.theoriginalbit.moarperipherals.common.reference.Constants;
import com.theoriginalbit.framework.peripheral.turtle.UpgradeTool;
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

import java.util.ArrayList;
import java.util.Random;

/**
 * @author theoriginalbit
 * @since 26/10/14
 */
public class UpgradeIgniter extends UpgradeTool {
    private final Random rand = new Random();

    public UpgradeIgniter() {
        super(ConfigHandler.upgradeIdIgniter, Constants.UPGRADE.IGNITER.getLocalised(), new ItemStack(Items.flint_and_steel));
    }

    @Override
    public IIcon getIcon(ITurtleAccess turtle, TurtleSide side) {
        return Items.flint_and_steel.getIconFromDamage(0);
    }

    protected String getAttackFailureMessage() {
        return "Cannot ignite block";
    }

    protected String getDigFailureMessage() {
        return "Nothing to dig";
    }

    @Override
    protected boolean canAttackEntity(Entity entity) {
        return false;
    }

    @Override
    protected ArrayList<ItemStack> attackEntity(ITurtleAccess turtle, Entity entity) {
        return null;
    }

    @Override
    protected boolean canAttackBlock(World world, int x, int y, int z, int dir, EntityPlayer turtle) {
        return turtle.canPlayerEdit(x, y, z, dir, craftingStack) && (world.isAirBlock(x, y, z) || world.getBlock(x, y, z) == Blocks.portal);
    }

    @Override
    protected ArrayList<ItemStack> attackBlock(World world, int x, int y, int z, int dir, EntityPlayer turtle) {
        if (turtle.canPlayerEdit(x, y, z, dir, craftingStack)) {
            if (world.isAirBlock(x, y, z)) {
                world.playSoundEffect((double) x + 0.5D, (double) y + 0.5D, (double) z + 0.5D, "fire.ignite", 1.0F, rand.nextFloat() * 0.4F + 0.8F);
                world.setBlock(x, y, z, Blocks.fire);
            } else if (world.getBlock(x, y, z) == Blocks.portal) {
                world.playAuxSFX(2001, x, y, z, Block.getIdFromBlock(Blocks.portal) + world.getBlockMetadata(x, y, z) * 4096);
                world.setBlock(x, y, z, Blocks.air);
            }
        }
        return null;
    }

    @Override
    protected boolean canHarvestBlock(World world, int x, int y, int z) {
        return false;
    }

    @Override
    protected ArrayList<ItemStack> harvestBlock(World world, int x, int y, int z) {
        return null;
    }

}
