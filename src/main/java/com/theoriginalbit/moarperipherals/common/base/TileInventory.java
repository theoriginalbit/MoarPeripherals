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
package com.theoriginalbit.moarperipherals.common.base;

import com.theoriginalbit.moarperipherals.common.util.InventoryUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

/**
 * @author theoriginalbit
 * @since 13/10/2014
 */
public abstract class TileInventory extends TileMoarP implements ISidedInventory {
    private final ItemStack[] inventory;

    public TileInventory(int size) {
        inventory = new ItemStack[size];
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);
        InventoryUtil.readInventoryFromNBT(this, tag);
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);
        InventoryUtil.writeInventoryToNBT(this, tag);
    }

    @Override
    public int getSizeInventory() {
        return inventory.length;
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        return inventory[slot];
    }

    @Override
    public ItemStack decrStackSize(int slot, int amount) {
        ItemStack stack = inventory[slot];
        if (stack != null) {
            if (stack.stackSize <= amount) {
                inventory[slot] = null;
            } else {
                stack = stack.splitStack(amount);
                if (stack.stackSize == 0) {
                    inventory[slot] = null;
                }
            }
            markDirty();
        }
        return stack;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int slot) {
        ItemStack stack = inventory[slot];
        if (stack != null) {
            inventory[slot] = null;
        }
        return stack;
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack stack) {
        inventory[slot] = stack;
        if (stack != null && stack.stackSize > getInventoryStackLimit()) {
            stack.stackSize = getInventoryStackLimit();
        }
        markDirty();
    }

    @Override
    public abstract String getInventoryName();

    @Override
    public boolean hasCustomInventoryName() {
        return false;
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player) {
        return worldObj.getTileEntity(xCoord, yCoord, zCoord) == this && player.getDistanceSq(xCoord + 0.5d, yCoord + 0.5d, zCoord + 0.5d) <= 64d;
    }

    @Override
    public void openInventory() {

    }

    @Override
    public void closeInventory() {

    }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack stack) {
        return false;
    }

    @Override
    public int[] getAccessibleSlotsFromSide(int side) {
        return InventoryUtil.makeSlotArray(this);
    }

    @Override
    public boolean canInsertItem(int slot, ItemStack stack, int side) {
        return false;
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack stack, int side) {
        return true;
    }
}
