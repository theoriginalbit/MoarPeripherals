/**
 * Copyright (c) 2013-2014, Joshua Asbury (@theoriginalbit)
 * http://wiki.theoriginalbit.com/moarperipherals/
 *
 * MoarPeripherals is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://www.mod-buildcraft.com/MMPL-1.0.txt
 */
package com.theoriginalbit.moarperipherals.common.utils;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;

import java.util.Random;

public final class InventoryUtils {
    private static final Random rand = new Random();

    public static int[] makeSlotArray(IInventory inv) {
        final int[] slots = new int[inv.getSizeInventory()];
        for (int i = 0; i < slots.length; i++) {
            slots[i] = i;
        }
        return slots;
    }

    public static void writeInventoryToNBT(IInventory inv, NBTTagCompound tag) {
        tag.setTag("Items", writeInventoryToNBT(inv));
    }

    public static NBTTagList writeInventoryToNBT(IInventory inv) {
        final NBTTagList list = new NBTTagList();
        for (int i = 0; i < inv.getSizeInventory(); ++i) {
            final ItemStack stack = inv.getStackInSlot(i);
            if (stack != null) {
                NBTTagCompound itemTag = new NBTTagCompound();
                itemTag.setByte("Slot", (byte) i);
                stack.writeToNBT(itemTag);
                list.appendTag(itemTag);
            }
        }
        return list;
    }

    public static void readInventoryFromNBT(IInventory inv, NBTTagCompound tag) {
        readInventoryFromNBT(inv, tag.getTagList("Items", 10));
    }

    public static void readInventoryFromNBT(IInventory inv, NBTTagList list) {
        for (int i = 0; i < list.tagCount(); ++i) {
            NBTTagCompound itemTag = list.getCompoundTagAt(i);
            int slot = itemTag.getByte("Slot") & 255;
            if (slot >= 0 && slot < inv.getSizeInventory()) {
                inv.setInventorySlotContents(slot, ItemStack.loadItemStackFromNBT(itemTag));
            }
        }
    }

    public static int findEmptySlot(IInventory inv, ItemStack stack) {
        for (int i = 0; i < inv.getSizeInventory(); ++i) {
            final ItemStack item = inv.getStackInSlot(i);
            if (item == null || (stack.isItemEqual(item) && (item.stackSize + stack.stackSize) <= item.getMaxStackSize())) {
                return i;
            }
        }
        return -1;
    }

    public static int findQtyOf(IInventory inv, ItemStack template) {
        int qty = 0;
        for (int i = 0; i < inv.getSizeInventory(); ++i) {
            ItemStack stack = inv.getStackInSlot(i);
            if (stack != null && stack.isItemEqual(template)) {
                qty += stack.stackSize;
            }
        }
        return qty;
    }

    public static ItemStack extract(IInventory inv, ItemStack template, int amount) {
        for (int i = 0; i < inv.getSizeInventory(); ++i) {
            ItemStack stack = inv.getStackInSlot(i);
            if (stack.isItemEqual(template)) {
                return inv.decrStackSize(i, amount);
            }
        }
        return null;
    }

    public static void spawnInWorld(ItemStack item, World world, int x, int y, int z) {
        if (item != null && item.stackSize > 0) {
            float rx = rand.nextFloat() * 0.8f + 0.1f;
            float ry = rand.nextFloat() * 0.8f + 0.1f;
            float rz = rand.nextFloat() * 0.8f + 0.1f;
            EntityItem entityItem = new EntityItem(world, x + rx, y + ry, z + rz, new ItemStack(item.getItem(), item.stackSize, item.getItemDamage()));
            if (item.hasTagCompound()) {
                entityItem.getEntityItem().setTagCompound((NBTTagCompound) item.getTagCompound().copy());
            }
            float factor = 0.05f;
            entityItem.motionX = rand.nextGaussian() * factor;
            entityItem.motionY = rand.nextGaussian() * factor + 0.2f;
            entityItem.motionZ = rand.nextGaussian() * factor;
            world.spawnEntityInWorld(entityItem);
            item.stackSize = 0;
        }
    }

    public static void explodeInventory(IInventory inv, World world, int x, int y, int z) {
        for (int i = 0; i < inv.getSizeInventory(); ++i) {
            spawnInWorld(inv.getStackInSlot(i), world, x, y, z);
        }
    }

}