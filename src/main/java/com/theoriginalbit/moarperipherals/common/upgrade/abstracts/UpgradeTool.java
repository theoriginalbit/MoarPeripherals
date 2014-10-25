/**
 * Copyright (c) 2013-2014, Joshua Asbury (@theoriginalbit)
 * http://wiki.theoriginalbit.com/moarperipherals/
 *
 * MoarPeripherals is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://www.mod-buildcraft.com/MMPL-1.0.txt
 */
package com.theoriginalbit.moarperipherals.common.upgrade.abstracts;

import com.theoriginalbit.moarperipherals.common.reference.Constants.LocalisationStore;
import com.theoriginalbit.moarperipherals.common.utils.InventoryUtils;
import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.api.turtle.*;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.Facing;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author theoriginalbit
 * @since 13/10/2014
 */
public abstract class UpgradeTool implements ITurtleUpgrade {
    private final int id;
    private final String adjective;

    protected final ItemStack itemStack;
    protected ITurtleAccess turtle;

    protected UpgradeTool(int upgradeId, LocalisationStore localisation, ItemStack stack) {
        id = upgradeId;
        itemStack = stack;
        adjective = localisation.getLocalised();
    }

    @Override
    public final int getUpgradeID() {
        return id;
    }

    @Override
    public final String getUnlocalisedAdjective() {
        return adjective;
    }

    @Override
    public final TurtleUpgradeType getType() {
        return TurtleUpgradeType.Tool;
    }

    @Override
    public final ItemStack getCraftingItem() {
        return itemStack;
    }

    @Override
    public final IPeripheral createPeripheral(ITurtleAccess turtle, TurtleSide side) {
        return null;
    }

    @Override
    public TurtleCommandResult useTool(ITurtleAccess turtle, TurtleSide side, TurtleVerb verb, int direction) {
        this.turtle = turtle;
        switch (verb) {
            case Attack:
                return attack(turtle, direction);
            case Dig:
                return dig(turtle, direction);
        }
        return TurtleCommandResult.failure("Unsupported action");
    }

    @Override
    public void update(ITurtleAccess turtle, TurtleSide side) {
        // NO-OP
    }

    protected abstract boolean canHarvestBlock(World world, int x, int y, int z);

    protected abstract ArrayList<ItemStack> harvestBlock(World world, int x, int y, int z);

    protected TurtleCommandResult dig(ITurtleAccess turtle, int dir) {
        final World world = turtle.getWorld();
        final ChunkCoordinates coordinates = turtle.getPosition();
        int x = coordinates.posX + Facing.offsetsXForSide[dir];
        int y = coordinates.posY + Facing.offsetsYForSide[dir];
        int z = coordinates.posZ + Facing.offsetsZForSide[dir];

        final Block block = world.getBlock(x, y, z);
        if (!world.isAirBlock(x, y, z) && block.getBlockHardness(world, x, y, z) > -1f && canHarvestBlock(world, x, y, z)) {
            final ArrayList<ItemStack> result = harvestBlock(world, x, y, z);
            if (result != null) {
                store(result);
                world.setBlockToAir(x, y, z);
                world.playAuxSFX(2001, x, y, z, Block.getIdFromBlock(block) + world.getBlockMetadata(x, y, z) * 4096);
                return TurtleCommandResult.success();
            }
        }

        return TurtleCommandResult.failure("Nothing to dig");
    }

    protected TurtleCommandResult attack(ITurtleAccess turtle, int dir) {
        final World world = turtle.getWorld();
        final ChunkCoordinates coordinates = turtle.getPosition();
        int x = coordinates.posX + Facing.offsetsXForSide[dir];
        int y = coordinates.posY + Facing.offsetsYForSide[dir];
        int z = coordinates.posZ + Facing.offsetsZForSide[dir];

        final EntityPlayer player = new PlayerTurtle(turtle);
        @SuppressWarnings("unchecked")
        final List<Entity> list = world.getEntitiesWithinAABBExcludingEntity(player, AxisAlignedBB.getBoundingBox(x, y, z, x + 1d, y + 1d, z + 1d));

        for (Entity entity : list) {
            if (entity instanceof IShearable) {
                final ItemStack shears = itemStack.copy();
                if (((IShearable) entity).isShearable(shears, entity.worldObj, (int) entity.posX, (int) entity.posY, (int) entity.posZ)) {
                    store(((IShearable) entity).onSheared(shears, entity.worldObj, (int) entity.posX, (int) entity.posY, (int) entity.posZ, 0));
                    return TurtleCommandResult.success();
                }
            }
        }

        return TurtleCommandResult.failure("Nothing to attack");
    }

    private void store(final ArrayList<ItemStack> list) {
        if (list != null) {
            for (final ItemStack stack : list) {
                store(stack);
            }
        }
    }

    private void store(final ItemStack stack) {
        if (!storeItemStack(stack)) {
            ChunkCoordinates coordinates = turtle.getPosition();
            int direction = turtle.getDirection();
            int x = coordinates.posX + Facing.offsetsXForSide[direction];
            int y = coordinates.posY + Facing.offsetsYForSide[direction];
            int z = coordinates.posZ + Facing.offsetsZForSide[direction];
            InventoryUtils.spawnInWorld(stack, turtle.getWorld(), x, y, z);
        }
    }

    protected boolean storeItemStack(ItemStack stack) {
        final IInventory inventory = turtle.getInventory();

        for (int i = 0; i < inventory.getSizeInventory(); ++i) {
            ItemStack currentStack = inventory.getStackInSlot(i);
            if (currentStack == null) {
                inventory.setInventorySlotContents(i, stack.copy());
                stack.stackSize = 0;
                return true;
            } else if (currentStack.isStackable() && currentStack.isItemEqual(stack)) {
                int space = currentStack.getMaxStackSize() - currentStack.stackSize;
                if (space >= stack.stackSize) {
                    currentStack.stackSize += stack.stackSize;
                    stack.stackSize = 0;
                    return true;
                } else {
                    currentStack.stackSize = currentStack.getMaxStackSize();
                    stack.stackSize -= space;
                }
            }
        }

        return false;
    }

}