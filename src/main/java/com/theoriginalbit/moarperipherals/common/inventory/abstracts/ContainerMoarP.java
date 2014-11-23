/**
 * Copyright 2014 Joshua Asbury (@theoriginalbit)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.theoriginalbit.moarperipherals.common.inventory.abstracts;

import com.theoriginalbit.moarperipherals.common.tile.abstracts.TileInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/**
 * @author theoriginalbit
 * @since 26/10/14
 */
public abstract class ContainerMoarP extends Container {
    public TileInventory inventory;

    public ContainerMoarP(TileInventory tile) {
        inventory = tile;
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return inventory.isUseableByPlayer(player);
    }

    @Override
    public abstract ItemStack transferStackInSlot(EntityPlayer player, int slot);

    @Override
    protected boolean mergeItemStack(ItemStack stack, int start, int end, boolean reverse) {
        return mergeItemStack(stack, start, end);
    }

    protected boolean mergeItemStack(ItemStack stack, int start, int end) {
        // TODO: fix this funky code
        for (int idx = start; idx < end; ++idx) {
            final Slot slot = (Slot) inventorySlots.get(idx);
            final int maxSize = Math.min(slot.getSlotStackLimit(), stack.getMaxStackSize());

            // if the item cannot be accepted, don't bother trying
            if (!slot.isItemValid(stack)) {
                continue;
            }

            final ItemStack itemStack = slot.getStack();
            if (stack.isStackable() && itemStack != null && itemStack.stackSize <= maxSize) { // if the item is stack-able
                int space = maxSize - itemStack.stackSize;
                if (space >= stack.stackSize) {
                    slot.putStack(stack.copy());
                    slot.onSlotChanged();
                    stack.stackSize = 0;
                    return true;
                } else if (space > 0) {
                    stack.stackSize -= space;
                    itemStack.stackSize += space;
                    slot.onSlotChanged();
                }
            } else if (itemStack == null) { // if the item is not stack-able and there's nothing in the slot
                slot.putStack(stack.copy());
                slot.onSlotChanged();
                stack.stackSize = 0;
                return true;
            }
        }

        return false;
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
