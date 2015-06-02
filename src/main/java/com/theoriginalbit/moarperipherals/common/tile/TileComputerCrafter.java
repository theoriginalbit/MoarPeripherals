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
package com.theoriginalbit.moarperipherals.common.tile;

import com.google.common.collect.Maps;
import com.theoriginalbit.framework.peripheral.annotation.LuaPeripheral;
import com.theoriginalbit.framework.peripheral.annotation.function.LuaFunction;
import com.theoriginalbit.framework.peripheral.annotation.function.MultiReturn;
import com.theoriginalbit.moarperipherals.client.gui.GuiType;
import com.theoriginalbit.moarperipherals.client.gui.IHasGui;
import com.theoriginalbit.moarperipherals.common.base.TileInventory;
import com.theoriginalbit.moarperipherals.common.reference.Constants;
import com.theoriginalbit.moarperipherals.common.util.InventoryUtil;
import dan200.computercraft.api.lua.LuaException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.nbt.NBTTagCompound;

import java.util.HashMap;

/**
 * @author theoriginalbit
 * @since 14/10/2014
 */
@LuaPeripheral("computer_crafter")
public class TileComputerCrafter extends TileInventory implements IHasGui {
    final CraftingManager manager = CraftingManager.getInstance();
    public InventoryCrafting craftingInv = new InventoryCrafting(new Container() {
        @Override
        public boolean canInteractWith(EntityPlayer player) {
            return true;
        }
    }, 3, 3);

    public TileComputerCrafter() {
        super(18);
    }

    @Override
    public String getInventoryName() {
        return Constants.GUI.CRAFTER.getLocalised();
    }

    @LuaFunction
    public int getInventorySize() {
        return getSizeInventory();
    }

    @LuaFunction("getStackInSlot")
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
        InventoryUtil.readInventoryFromNBT(craftingInv, tag.getTagList("craft", 10));
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);
        // make sure the crafting inventory is persistent across world restarts
        tag.setTag("craft", InventoryUtil.writeInventoryToNBT(craftingInv));
    }

    @Override
    public GuiType getGuiId() {
        return GuiType.CRAFTER;
    }

    @LuaFunction
    public boolean isRecipeValid() {
        return manager.findMatchingRecipe(craftingInv, worldObj) != null;
    }

    @LuaFunction
    public ItemStack getCraftingSlot(int slot) throws LuaException {
        --slot; // convert from Lua indexes that start at 1
        if (slot >= 0 && slot < craftingInv.getSizeInventory()) {
            return craftingInv.getStackInSlot(slot);
        }
        return null;
    }

    @LuaFunction
    @MultiReturn
    public Object[] setCraftingSlot(int slot, ItemStack stack) {
        --slot; // convert from Lua indexes that start at 1
        if (slot < 0 || slot > craftingInv.getSizeInventory()) {
            return new Object[]{false, "expected slot between 1 and " + craftingInv.getSizeInventory()};
        }
        stack.stackSize = 1;
        craftingInv.setInventorySlotContents(slot, stack);
        return new Object[]{true};
    }

    @LuaFunction
    public Object[] clearCraftingSlot(int slot) {
        --slot; // convert from Lua indexes that start at 1
        if (slot < 0 || slot > craftingInv.getSizeInventory()) {
            return new Object[]{false, "expected slot between 1 and " + craftingInv.getSizeInventory()};
        }
        craftingInv.setInventorySlotContents(slot, null);
        return new Object[]{true};
    }

    @LuaFunction
    public void clearCraftingGrid() {
        for (int i = 0; i < craftingInv.getSizeInventory(); ++i) {
            craftingInv.setInventorySlotContents(i, null);
        }
    }

    @LuaFunction
    @MultiReturn
    public Object[] craft() {
        try {
            doCraft();
            return new Object[]{true};
        } catch (Exception e) {
            return new Object[]{false, e.getMessage()};
        }
    }

    public void doCraft() throws Exception {
        final ItemStack result = manager.findMatchingRecipe(craftingInv, worldObj);
        if (result == null) {
            throw new Exception("invalid recipe");
        }

        // count all the items that we need
        final HashMap<Class<? extends Item>, Integer> counts = Maps.newHashMap();
        for (int slot = 0; slot < craftingInv.getSizeInventory(); ++slot) {
            final ItemStack itemStack = craftingInv.getStackInSlot(slot);
            if (itemStack != null) {
                final Class<? extends Item> itemClass = itemStack.getItem().getClass();
                if (!counts.containsKey(itemClass)) {
                    counts.put(itemClass, 0);
                }
                counts.put(itemClass, counts.get(itemClass) + 1);
            }
        }

        // make sure we have those items
        for (int slot = 0; slot < craftingInv.getSizeInventory(); ++slot) {
            final ItemStack itemStack = craftingInv.getStackInSlot(slot);
            if (itemStack != null) {
                final Class<? extends Item> itemClass = itemStack.getItem().getClass();
                if (InventoryUtil.findQtyOf(this, itemStack) < counts.get(itemClass)) {
                    throw new Exception("cannot craft, missing items");
                }
            }
        }

        // consume items
        for (int slot = 0; slot < craftingInv.getSizeInventory(); ++slot) {
            final ItemStack itemStack = craftingInv.getStackInSlot(slot);
            if (itemStack != null) {
                InventoryUtil.takeItems(this, itemStack, 1);
            }
        }

        // add the 'crafted' item to the inventory, or pop into the world
        InventoryUtil.storeOrDropItemStack(this, result, worldObj, xCoord, yCoord, zCoord);
    }
}
