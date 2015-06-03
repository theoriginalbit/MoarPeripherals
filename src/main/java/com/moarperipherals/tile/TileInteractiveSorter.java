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
package com.moarperipherals.tile;

import com.google.common.collect.Lists;
import com.theoriginalbit.framework.peripheral.annotation.Computers;
import com.theoriginalbit.framework.peripheral.annotation.LuaPeripheral;
import com.theoriginalbit.framework.peripheral.annotation.function.LuaFunction;
import com.moarperipherals.tile.sorter.Side;
import com.moarperipherals.client.gui.GuiType;
import com.moarperipherals.client.gui.IHasGui;
import com.moarperipherals.Constants;
import com.moarperipherals.util.InventoryUtil;
import dan200.computercraft.api.peripheral.IComputerAccess;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Facing;
import org.apache.commons.lang3.ArrayUtils;

import java.util.ArrayList;
import java.util.List;

@LuaPeripheral("interactive_sorter")
public class TileInteractiveSorter extends TileInventory implements IHasGui {
    private static final String EVENT_SORT = "sort";

    @Computers.List
    public ArrayList<IComputerAccess> computers;

    public TileInteractiveSorter() {
        super(1);
    }

    @Override
    public boolean blockActivated(EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
        player.displayGUIChest(this);
        return true;
    }

    @Override
    public GuiType getGuiId() {
        return GuiType.SINGLE_SLOT;
    }

    @Override
    public String getInventoryName() {
        return Constants.GUI.SORTER.getLocalised();
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack stack) {
        super.setInventorySlotContents(slot, stack);
        queueEvent(EVENT_SORT);
    }

    @LuaFunction
    public int sort(Side side, int amount) {
        // stop early if the amount is too low
        if (amount <= 0) return amount;

        // get the item in the sorter
        final ItemStack stack = getItem();
        if (stack == null) return 0;

        // validate the amount
        amount = Math.min(stack.stackSize, amount);

        // make sure the tile on that side is an inventory
        final IInventory inventory = getInventoryForSide(side);
        if (inventory == null) return 0;

        // output the items
        final ItemStack remainder = InventoryUtil.storeItemStack(inventory, stack.splitStack(amount));

        // remove or update the stack in the inventory sorter
        setInventorySlotContents(0, stack.stackSize > 0 ? stack : null);

        // return how many items were sorted
        return amount - remainder.stackSize;
    }

    @LuaFunction
    public List<ItemStack> list(Side side) {
        // make sure the tile on that side is an inventory
        final IInventory inventory = getInventoryForSide(side);
        if (inventory == null) return null;

        // get each item in the inventory
        final List<ItemStack> items = Lists.newArrayList();
        for (int i = 0; i < inventory.getSizeInventory() - 1; ++i) {
            items.add(inventory.getStackInSlot(i));
        }

        // return the item list
        return items;
    }

    @LuaFunction
    public boolean extract(Side from, Side to, int slot, int amount) {
        // reject the 'from' and 'to' being the same size
        if (from == to) return false;
        // stop early if the amount is too low
        if (amount <= 0) return false;

        // get the 'from' and 'to' inventories
        final IInventory fromInv = getInventoryForSide(from);
        final IInventory toInv = getInventoryForSide(to);
        if (fromInv == null || toInv == null) return false;

        // validate the supplied slot is valid for the 'fromInv'
        if (slot < 1 || slot > fromInv.getSizeInventory()) return false;

        // get the stack from the origin
        final ItemStack stack = fromInv.getStackInSlot(--slot); // convert from Lua index to Java index
        if (stack == null) return false;

        // validate the stack amount
        amount = Math.min(stack.stackSize, amount);

        // add the item stack to the 'toInv' or explode it into the world
        final ItemStack output = stack.splitStack(amount);
        InventoryUtil.storeOrDropItemStack(toInv, output, worldObj, xCoord, yCoord, zCoord);

        // remove or update the origin stack
        fromInv.setInventorySlotContents(slot, stack.stackSize > 0 ? stack : null);

        return true;
    }

    @LuaFunction
    public ItemStack getItem() {
        return getStackInSlot(0);
    }

    private IInventory getInventoryForSide(Side side) {
        // get the location from the side
        int direction = side.ordinal();
        int x = xCoord + Facing.offsetsXForSide[direction];
        int y = yCoord + Facing.offsetsYForSide[direction];
        int z = zCoord + Facing.offsetsZForSide[direction];
        // get the tile for the offset
        TileEntity tile = worldObj.getTileEntity(x, y, z);
        return tile instanceof IInventory ? (IInventory) tile : null;
    }

    protected void queueEvent(String event, Object... args) {
        if (computers != null) {
            for (IComputerAccess computer : computers) {
                computer.queueEvent(event, ArrayUtils.add(args, 0, computer.getAttachmentName()));
            }
        }
    }
}
