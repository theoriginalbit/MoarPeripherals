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
import com.moarperipherals.Constants;
import com.moarperipherals.api.sorter.IInteractiveSorterOutput;
import com.moarperipherals.api.sorter.IInteractiveSorterRegistry;
import com.moarperipherals.client.gui.GuiType;
import com.moarperipherals.client.gui.IHasGui;
import com.moarperipherals.registry.InteractiveSorterRegistry;
import com.moarperipherals.util.InventoryUtil;
import com.theoriginalbit.framework.peripheral.annotation.Computers;
import com.theoriginalbit.framework.peripheral.annotation.LuaPeripheral;
import com.theoriginalbit.framework.peripheral.annotation.function.LuaFunction;
import dan200.computercraft.api.lua.LuaException;
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

    static {
        InteractiveSorterRegistry.INSTANCE.register(new DefaultSorterOutput());
    }

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

        final TileEntity tile = getTileForSide(side);
        if (tile == null) return 0;

        return sort(stack, amount, tile, this);
    }

    @LuaFunction
    public List<ItemStack> list(Side side) {
        // check if there is an inventory
        final TileEntity tile = getTileForSide(side);
        if (!(tile instanceof IInventory)) return null;

        // make sure the tile on that side is an inventory
        final IInventory inventory = (IInventory) tile;

        // get each item in the inventory
        final List<ItemStack> items = Lists.newArrayList();
        for (int i = 0; i < inventory.getSizeInventory() - 1; ++i) {
            items.add(inventory.getStackInSlot(i));
        }

        // return the item list
        return items;
    }

    @LuaFunction
    public int extract(Side from, Side to, int slot, int amount) throws LuaException {
        // reject the 'from' and 'to' being the same size
        if (from == to) return 0;
        // stop early if the amount is too low
        if (amount <= 0) return 0;

        // check if there is an inventory
        final TileEntity fromTile = getTileForSide(from);
        if (!(fromTile instanceof IInventory)) return 0;

        // make sure the tile on that side is an inventory
        final IInventory fromInv = (IInventory) fromTile;

        // validate the supplied slot is valid for the 'fromInv'
        if (slot < 1 || slot > fromInv.getSizeInventory())
            throw new LuaException("expected slot 1-" + fromInv.getSizeInventory());

        // get the stack from the origin
        final ItemStack stack = fromInv.getStackInSlot(--slot); // convert from Lua index to Java index
        if (stack == null) return 0;

        // validate the stack amount
        amount = Math.min(stack.stackSize, amount);

        final TileEntity toTile = getTileForSide(to);
        if (toTile == null) return 0;

        return sort(stack, amount, toTile, fromInv);
    }

    @LuaFunction
    public ItemStack getItem() {
        return getStackInSlot(0);
    }

    private TileEntity getTileForSide(Side side) {
        // get the location from the side
        int direction = side.ordinal();
        int x = xCoord + Facing.offsetsXForSide[direction];
        int y = yCoord + Facing.offsetsYForSide[direction];
        int z = zCoord + Facing.offsetsZForSide[direction];
        // get the tile for the offset
        return worldObj.getTileEntity(x, y, z);
    }

    private int sort(ItemStack stack, int amount, TileEntity target, IInventory source) {
        final IInteractiveSorterRegistry registry = InteractiveSorterRegistry.INSTANCE;

        final ItemStack outputStack = stack.copy().splitStack(amount);

        for (int i = 0; i < registry.size(); ++i) {
            final IInteractiveSorterOutput output = registry.getSorterOutput(i);
            int sorted = output.output(outputStack, target);
            if (sorted > 0) {
                stack.stackSize -= sorted;
                // remove or update the stack in the inventory sorter
                source.setInventorySlotContents(0, stack.stackSize > 0 ? stack : null);
                // return how many items were sorted
                return sorted;
            }
        }

        return 0;
    }

    protected void queueEvent(String event, Object... args) {
        if (computers != null) {
            for (IComputerAccess computer : computers) {
                computer.queueEvent(event, ArrayUtils.add(args, 0, computer.getAttachmentName()));
            }
        }
    }

    public enum Side {
        BLUE, GREEN, ORANGE, PURPLE, RED, YELLOW
    }

    private static class DefaultSorterOutput implements IInteractiveSorterOutput {
        @Override
        public int output(ItemStack stack, TileEntity tile) {
            // make sure it's an inventory
            if (!(tile instanceof IInventory)) return 0;
            // try store the stack
            ItemStack remainder = InventoryUtil.storeItemStack((IInventory) tile, stack);
            return stack.stackSize - (remainder == null ? 0 : remainder.stackSize);
        }
    }
}
