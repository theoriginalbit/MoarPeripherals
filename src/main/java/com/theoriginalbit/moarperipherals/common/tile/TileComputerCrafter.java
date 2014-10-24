/**
 * Copyright (c) 2013-2014, Joshua Asbury (@theoriginalbit)
 * http://wiki.theoriginalbit.com/moarperipherals/
 *
 * MoarPeripherals is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://www.mod-buildcraft.com/MMPL-1.0.txt
 */
package com.theoriginalbit.moarperipherals.common.tile;

import com.theoriginalbit.framework.peripheral.annotation.LuaFunction;
import com.theoriginalbit.framework.peripheral.annotation.LuaPeripheral;
import com.theoriginalbit.moarperipherals.api.tile.aware.IActivateAwareTile;
import com.theoriginalbit.moarperipherals.api.tile.aware.IBreakAwareTile;
import com.theoriginalbit.moarperipherals.common.reference.Constants;
import com.theoriginalbit.moarperipherals.common.tile.abstracts.TileInventory;
import com.theoriginalbit.moarperipherals.common.utils.InventoryUtils;
import dan200.computercraft.api.lua.LuaException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.nbt.NBTTagCompound;

/**
 * @author theoriginalbit
 * @since 14/10/2014
 */
@LuaPeripheral("computer_crafter")
public class TileComputerCrafter extends TileInventory implements IActivateAwareTile, IBreakAwareTile {
    public InventoryCrafting crafting = new InventoryCrafting(new Container() {
        @Override
        public boolean canInteractWith(EntityPlayer player) {
            return true;
        }
    }, 3, 3);

    final CraftingManager manager = CraftingManager.getInstance();

    public TileComputerCrafter() {
        super(27);
    }

    @LuaFunction(isMultiReturn = true)
    public Object[] setCraftingSlot(int slot, ItemStack stack) {
        --slot; // convert from Lua indexes that start at 1
        if (slot < 0 || slot > crafting.getSizeInventory()) {
            return new Object[]{false, "expected slot between 1 and " + crafting.getSizeInventory()};
        }
        if (InventoryUtils.findQtyOf(this, stack) < stack.stackSize) {
            return new Object[]{false, "no item "};
        }
        crafting.setInventorySlotContents(slot, stack);
        return new Object[]{true};
    }

    @LuaFunction(isMultiReturn = true)
    public Object[] craft() {
        // TODO: make sure the items are in the inventory

        final ItemStack result = manager.findMatchingRecipe(crafting, worldObj);
        if (result == null) {
            return new Object[]{false, "no matching recipe"};
        }

        // see if it can fit in the inventory
        int slot = findEmptySlot(result);

        // explode into the world
        if (slot == -1) {
            return new Object[]{false, "no space in inventory"};
        }

        // TODO: maybe consume the items, we shall see

        final ItemStack stack = getStackInSlot(slot);
        if (stack != null) {
            stack.stackSize += result.stackSize;
        } else {
            setInventorySlotContents(slot, result);
        }

        return new Object[]{true};
    }

    @LuaFunction
    public boolean canCraft() {
        return manager.findMatchingRecipe(crafting, worldObj) != null;
    }

    @LuaFunction
    public ItemStack getCraftingSlot(int slot) throws LuaException {
        --slot; // convert from Lua indexes that start at 1
        if (slot >= 0 || slot <= crafting.getSizeInventory()) {
            return crafting.getStackInSlot(slot);
        }
        return null;
    }

    private int findEmptySlot(ItemStack stack) {
        for (int i = 0; i < getSizeInventory(); ++i) {
            final ItemStack item = getStackInSlot(i);
            if (item == null || (stack.isItemEqual(item) && (item.stackSize + stack.stackSize) <= item.getMaxStackSize())) {
                return i;
            }
        }
        return -1;
    }

    @LuaFunction
    public int getInventorySize() {
        return getSizeInventory();
    }

    @LuaFunction(name = "getStackInSlot")
    public ItemStack stackInSlot(int slot) {
        // convert from Lua indexes that start at 1
        --slot;
        if (slot >= 0 || slot <= getSizeInventory()) {
            return getStackInSlot(slot);
        }
        return null;
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);
        // make sure the crafting inventory is persistent across world restarts
        InventoryUtils.readInventoryFromNBT(crafting, tag.getTagList("craft", 10));
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);
        // make sure the crafting inventory is persistent across world restarts
        tag.setTag("craft", InventoryUtils.writeInventoryToNBT(crafting));
    }

    @Override
    public String getInventoryName() {
        return Constants.GUI.CRAFTER.getLocalised();
    }

    @Override
    public boolean onActivated(EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
        player.displayGUIChest(this);
        return true;
    }

    @Override
    public void onBreak(int x, int y, int z) {
        // make sure they get the crafting stuff back
        InventoryUtils.explodeInventory(crafting, worldObj, x, y, z);
    }
}
