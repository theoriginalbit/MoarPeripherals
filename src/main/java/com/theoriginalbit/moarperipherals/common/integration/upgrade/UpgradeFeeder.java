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
package com.theoriginalbit.moarperipherals.common.integration.upgrade;

import com.theoriginalbit.framework.peripheral.exception.TurtleFailureAttack;
import com.theoriginalbit.framework.peripheral.exception.TurtleFailureDig;
import com.theoriginalbit.framework.peripheral.turtle.UpgradeTool;
import com.theoriginalbit.moarperipherals.common.config.ConfigData;
import com.theoriginalbit.moarperipherals.common.reference.Constants;
import dan200.computercraft.api.turtle.ITurtleAccess;
import dan200.computercraft.api.turtle.TurtleSide;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import java.util.ArrayList;

/**
 * @author theoriginalbit
 * @since 14/11/14
 */
public class UpgradeFeeder extends UpgradeTool {
    private static final ItemStack wheat = new ItemStack(Items.wheat);

    public UpgradeFeeder() {
        super(ConfigData.upgradeIdFeeder, Constants.UPGRADE.FEEDER.getLocalised(), wheat);
    }

    @Override
    public IIcon getIcon(ITurtleAccess turtle, TurtleSide side) {
        return wheat.getIconIndex();
    }

    @Override
    protected boolean canAttackEntity(Entity entity) {
        return entity instanceof EntityAnimal;
    }

    @Override
    protected ArrayList<ItemStack> attackEntity(ITurtleAccess turtle, Entity entity) throws TurtleFailureAttack {
        final IInventory inv = turtle.getInventory();
        final int selected = turtle.getSelectedSlot();
        final ItemStack currStack = inv.getStackInSlot(selected);
        if (currStack == null || currStack.stackSize <= 0) {
            throw new TurtleFailureAttack("selected slot empty");
        }
        final EntityAnimal animal = (EntityAnimal) entity;
        if (animal.getGrowingAge() == 0 && !animal.isInLove() && animal.isBreedingItem(currStack)) {
            animal.setTarget(null); // remove anything they're following
            animal.func_146082_f(null); // set it in love
            --currStack.stackSize;
            if (currStack.stackSize == 0) {
                inv.setInventorySlotContents(selected, null);
            }
        }
        return null;
    }

    @Override
    protected AxisAlignedBB getEntitySearchAABB(ITurtleAccess turtle, int dir) {
        final ChunkCoordinates coordinates = turtle.getPosition();
        int x = coordinates.posX;
        int y = coordinates.posY;
        int z = coordinates.posZ;
        return AxisAlignedBB.getBoundingBox(
                x - 1.5d,
                y - 1.5d,
                z - 1.5d,
                x + 1.5d,
                y + 1.5d,
                z + 1.5d
        );
    }

    @Override
    protected boolean canAttackBlock(World world, int x, int y, int z, int dir, EntityPlayer turtle) {
        return false;
    }

    @Override
    protected ArrayList<ItemStack> attackBlock(World world, int x, int y, int z, int dir, EntityPlayer turtle) throws TurtleFailureAttack {
        throw new TurtleFailureAttack("How did this happen!");
    }

    @Override
    protected boolean canHarvestBlock(World world, int x, int y, int z) {
        return false;
    }

    @Override
    protected ArrayList<ItemStack> harvestBlock(World world, int x, int y, int z) throws TurtleFailureDig {
        throw new TurtleFailureDig("How did this happen!");
    }
}
