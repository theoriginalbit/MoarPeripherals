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
package com.moarperipherals.util;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;

import java.util.Random;

public final class InventoryUtil {
    private static final Random rand = new Random();

    public static boolean areItemsStackable(ItemStack a, ItemStack b) {
        if (a == null || b == null) {
            return false;
        }
        if (a == b) {
            return true;
        }

        if (a.getItem() == b.getItem()) {
            if (((!a.getHasSubtypes()) && (!a.isItemStackDamageable())) || (a.getItemDamage() == b.getItemDamage())) {
                if (a.stackTagCompound == null && b.stackTagCompound == null) {
                    return true;
                }
                if (a.stackTagCompound != null && b.stackTagCompound != null && a.stackTagCompound.equals(b.stackTagCompound)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean canStoreItem(IInventory inv, ItemStack stack) {
        int count = stack.stackSize;

        for (int slot = 0; slot < inv.getSizeInventory(); ++slot) {
            final ItemStack contents = inv.getStackInSlot(slot);
            if (contents == null) {
                return true;
            } else if (areItemsStackable(contents, stack)) {
                int space = Math.min(contents.getMaxStackSize(), inv.getInventoryStackLimit()) - contents.stackSize;
                if (space >= count) {
                    return true;
                }
                if (space > 0) {
                    count -= space;
                }
            }
        }

        return count == 0;
    }

    public static void storeOrDropItemStack(IInventory inv, ItemStack stack, World world, ChunkCoordinates coords) {
        storeOrDropItemStack(inv, stack, world, coords.posX, coords.posY, coords.posZ);
    }

    public static void storeOrDropItemStack(IInventory inv, ItemStack stack, World world, int x, int y, int z) {
        final ItemStack remainder = storeItemStack(inv, stack);
        if (remainder != null && remainder.stackSize > 0) {
            spawnItemStackInWorld(stack, world, x, y, z);
        }
    }

    public static ItemStack storeItemStack(IInventory inv, ItemStack stack) {
        if (stack == null || stack.stackSize == 0) {
            return null;
        }

        ItemStack remainder = stack;

        for (int slot = 0; slot < inv.getSizeInventory(); ++slot) {
            final ItemStack contents = inv.getStackInSlot(slot);
            if (contents == null) {
                int space = inv.getInventoryStackLimit();
                if (space >= remainder.stackSize) {
                    inv.setInventorySlotContents(slot, remainder);
                    inv.markDirty();
                    return null;
                }

                remainder = remainder.copy();
                inv.setInventorySlotContents(slot, remainder.splitStack(space));
            } else if (areItemsStackable(contents, remainder)) {
                int space = Math.min(contents.getMaxStackSize(), inv.getInventoryStackLimit()) - contents.stackSize;
                if (space >= remainder.stackSize) {
                    contents.stackSize += remainder.stackSize;
                    inv.setInventorySlotContents(slot, contents);
                    inv.markDirty();
                    return null;
                }
                if (space > 0) {
                    remainder = remainder.copy();
                    remainder.stackSize -= space;
                    contents.stackSize += space;
                    inv.setInventorySlotContents(slot, contents);
                }
            }
        }

        if (remainder != stack) {
            inv.markDirty();
        }

        return remainder;
    }

    public static ItemStack takeItems(IInventory inv, ItemStack stack, int amount) {
        if (stack == null || amount <= 0) {
            return null;
        }

        ItemStack partial = stack.copy();
        partial.stackSize = 0;

        int remaining = Math.min(amount, stack.getMaxStackSize());
        for (int slot = 0; slot < inv.getSizeInventory(); ++slot) {
            if (remaining > 0) {
                ItemStack contents = inv.getStackInSlot(slot);
                if (contents != null && areItemsStackable(contents, stack)) {
                    if (contents.stackSize > remaining) {
                        contents.stackSize -= remaining;
                        partial.stackSize += remaining;
                        remaining = 0;
                    } else {
                        partial.stackSize += contents.stackSize;
                        contents.stackSize = 0;
                        remaining = amount - partial.stackSize;
                    }

                    if (contents.stackSize == 0) {
                        inv.setInventorySlotContents(slot, null);
                    }
                }
            }
        }

        if (partial.stackSize > 0 && amount > 0) {
            inv.markDirty();
            return partial;
        }

        return null;
    }

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

    public static int findQtyOf(IInventory inv, ItemStack stack) {
        int qty = 0;
        for (int i = 0; i < inv.getSizeInventory(); ++i) {
            final ItemStack item = inv.getStackInSlot(i);
            if (areItemsStackable(item, stack)) {
                qty += item.stackSize;
            }
        }
        return qty;
    }

    public static void explodeInventory(IInventory inv, World world, int x, int y, int z) {
        for (int i = 0; i < inv.getSizeInventory(); ++i) {
            spawnItemStackInWorld(inv.getStackInSlot(i), world, x, y, z);
        }
    }

    public static void spawnItemStackInWorld(ItemStack stack, World world, int x, int y, int z) {
        if (stack != null && stack.stackSize > 0) {
            float rx = rand.nextFloat() * 0.8f + 0.1f;
            float ry = rand.nextFloat() * 0.8f + 0.1f;
            float rz = rand.nextFloat() * 0.8f + 0.1f;
            EntityItem entityItem = new EntityItem(world, x + rx, y + ry, z + rz, new ItemStack(stack.getItem(), stack.stackSize, stack.getItemDamage()));
            if (stack.hasTagCompound()) {
                entityItem.getEntityItem().setTagCompound((NBTTagCompound) stack.getTagCompound().copy());
            }
            float factor = 0.05f;
            entityItem.motionX = rand.nextGaussian() * factor;
            entityItem.motionY = rand.nextGaussian() * factor + 0.2f;
            entityItem.motionZ = rand.nextGaussian() * factor;
            world.spawnEntityInWorld(entityItem);
            stack.stackSize = 0;
        }
    }

}