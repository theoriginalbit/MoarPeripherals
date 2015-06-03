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
package com.moarperipherals.inventory;

import com.moarperipherals.tile.TileInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/**
 * @author theoriginalbit
 * @since 26/10/14
 */
public class ContainerMoarP extends Container {
    public TileInventory inventory;

    public ContainerMoarP(TileInventory tile) {
        inventory = tile;
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return inventory.isUseableByPlayer(player);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int idx) {
        ItemStack stack = null;
        Slot slot = (Slot) inventorySlots.get(idx);

        if (slot != null && slot.getHasStack()) {
            ItemStack itemStack = slot.getStack();
            stack = itemStack.copy();

            if (idx < inventory.getSizeInventory()) {
                if (!mergeItemStack(itemStack, inventory.getSizeInventory(), inventorySlots.size(), true)) {
                    return null;
                }
            } else if (!mergeItemStack(itemStack, 0, inventory.getSizeInventory(), false)) {
                return null;
            }

            if (itemStack.stackSize == 0) {
                slot.putStack(null);
            } else {
                slot.onSlotChanged();
            }
        }

        return stack;
    }

    /*
     * Modified version of Minecraft's code that actually respects the Slot#isItemValid methods
     */
    @Override
    protected boolean mergeItemStack(ItemStack stack, int start, int end, boolean reverse) {
        boolean partialMerge = false;
        int idx = (reverse ? end - 1 : start);

        Slot slot;
        ItemStack slotStack;

        if (stack.isStackable()) {
            while (stack.stackSize > 0 && (!reverse && idx < end || reverse && idx >= start)) {
                slot = (Slot) inventorySlots.get(idx);

                if (!slot.isItemValid(stack)) {
                    idx += (reverse ? -1 : 1);
                    continue;
                }

                slotStack = slot.getStack();

                if (slotStack != null && slotStack.getItem() == stack.getItem() && (!stack.getHasSubtypes() || stack.getItemDamage() == slotStack.getItemDamage()) && ItemStack.areItemStackTagsEqual(stack, slotStack)) {
                    int total = slotStack.stackSize + stack.stackSize;

                    if (total <= stack.getMaxStackSize() && total <= slot.getSlotStackLimit()) {
                        stack.stackSize = 0;
                        slotStack.stackSize = total;
                        slot.onSlotChanged();
                        partialMerge = true;
                    } else if (slotStack.stackSize < stack.getMaxStackSize() && total < slot.getSlotStackLimit()) {
                        stack.stackSize -= stack.getMaxStackSize() - slotStack.stackSize;
                        slotStack.stackSize = stack.getMaxStackSize();
                        slot.onSlotChanged();
                        partialMerge = true;
                    }
                }

                idx += (reverse ? -1 : 1);
            }
        }

        if (stack.stackSize > 0) {
            idx = (reverse ? end - 1 : start);

            while (!reverse && idx < end || reverse && idx >= start) {
                slot = (Slot) inventorySlots.get(idx);

                if (!slot.isItemValid(stack)) {
                    idx += (reverse ? -1 : 1);
                    continue;
                }

                slotStack = slot.getStack();

                if (slotStack == null) {
                    if (stack.stackSize <= slot.getSlotStackLimit()) {
                        slot.putStack(stack.copy());
                        stack.stackSize = 0;
                        slot.onSlotChanged();
                        partialMerge = true;
                        break;
                    } else {
                        putStackInSlot(idx, new ItemStack(stack.getItem(), slot.getSlotStackLimit(), stack.getItemDamage()));
                        stack.stackSize -= slot.getSlotStackLimit();
                        slot.onSlotChanged();
                        partialMerge = true;
                    }
                }

                idx += (reverse ? -1 : 1);
            }
        }

        return partialMerge;
    }

    protected void bindPlayerInventory(InventoryPlayer inventory, int offsetY) {
        for (int x = 0; x < 9; x++) {
            addSlotToContainer(new Slot(inventory, x, 8 + x * 18, offsetY + 58));
        }

        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 9; x++) {
                addSlotToContainer(new Slot(inventory, x + y * 9 + 9, 8 + x * 18, offsetY + y * 18));
            }
        }
    }
}
