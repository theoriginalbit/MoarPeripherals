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
import com.theoriginalbit.moarperipherals.api.tile.IHasGui;
import com.theoriginalbit.moarperipherals.api.tile.aware.IActivateAwareTile;
import com.theoriginalbit.moarperipherals.api.tile.aware.IBreakAwareTile;
import com.theoriginalbit.moarperipherals.client.gui.GuiType;
import com.theoriginalbit.moarperipherals.common.reference.Constants;
import com.theoriginalbit.moarperipherals.common.tile.abstracts.TileInventory;
import com.theoriginalbit.moarperipherals.common.utils.InventoryUtils;
import cpw.mods.fml.common.registry.GameRegistry;
import dan200.computercraft.api.lua.LuaException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.nbt.NBTTagCompound;

/**
 * @author theoriginalbit
 * @since 14/10/2014
 */
@LuaPeripheral("computer_crafter")
public class TileComputerCrafter extends TileInventory implements IActivateAwareTile, IBreakAwareTile, IHasGui {
    public InventoryCrafting craftingInv = new InventoryCrafting(new Container() {
        @Override
        public boolean canInteractWith(EntityPlayer player) {
            return true;
        }
    }, 3, 3);

    final CraftingManager manager = CraftingManager.getInstance();

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
        InventoryUtils.readInventoryFromNBT(craftingInv, tag.getTagList("craft", 10));
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);
        // make sure the crafting inventory is persistent across world restarts
        tag.setTag("craft", InventoryUtils.writeInventoryToNBT(craftingInv));
    }

    @Override
    public boolean onActivated(EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
        player.displayGUIChest(this);
        return true;
    }

    @Override
    public void onBreak(int x, int y, int z) {
        // make sure they get the crafting stuff back
        InventoryUtils.explodeInventory(craftingInv, worldObj, x, y, z);
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
        if (slot >= 0 || slot <= craftingInv.getSizeInventory()) {
            return craftingInv.getStackInSlot(slot);
        }
        return null;
    }

    @LuaFunction(isMultiReturn = true)
    public Object[] setCraftingSlot(int slot, String modId, String blockName) {
        return setCraftingSlotInternal(slot, modId, blockName, 0);
    }

    @LuaFunction(isMultiReturn = true)
    public Object[] setCraftingSlotWithMeta(int slot, String modId, String blockName, int meta) {
        return setCraftingSlotInternal(slot, modId, blockName, meta);
    }

    @LuaFunction
    public void clearCrafting() {
        for (int i = 0; i < craftingInv.getSizeInventory(); ++i) {
            craftingInv.setInventorySlotContents(i, null);
        }
    }

    @LuaFunction(isMultiReturn = true)
    public Object[] craft() {
        // TODO: make sure the items are in the inventory

        final ItemStack result = manager.findMatchingRecipe(craftingInv, worldObj);
        if (result == null) {
            return new Object[]{false, "no matching recipe"};
        }

        // TODO: maybe consume the items, we shall see

        InventoryUtils.storeOrDropItemStack(this, result, worldObj, xCoord, yCoord, zCoord);

        return new Object[]{true};
    }

    public ItemStack craft(int amount) {
        return null;
    }

    private final Object[] setCraftingSlotInternal(int slot, String modId, String blockName, int meta) {
        --slot; // convert from Lua indexes that start at 1
        if (slot < 0 || slot > craftingInv.getSizeInventory()) {
            return new Object[]{false, "expected slot between 1 and " + craftingInv.getSizeInventory()};
        }
        final Item item = GameRegistry.findItem(modId, blockName);
        if (item != null) {
            craftingInv.setInventorySlotContents(slot, new ItemStack(item, 1, meta));
            return new Object[]{true};
        }
        return new Object[]{false, "No block found for " + modId + ":" + blockName};
    }

}
