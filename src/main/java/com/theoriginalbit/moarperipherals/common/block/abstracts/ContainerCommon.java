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
package com.theoriginalbit.moarperipherals.common.block.abstracts;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/**
 * @author theoriginalbit
 * @since 26/10/14
 */
public class ContainerCommon extends Container {
    public EntityPlayer player;
    public IInventory inventory;

    public ContainerCommon(EntityPlayer player, IInventory inventory) {
        this(player, inventory, 84);
    }

    public ContainerCommon(EntityPlayer player, IInventory inventory, int inventoryBaseY) {
        this.player = player;
        this.inventory = inventory;

        for (int y = 0; y < 3; ++y) {
            for (int x = 0; x < 9; ++x) {
                addSlotToContainer(new Slot(player.inventory, x + y * 9 + 9, 8 + x * 18, inventoryBaseY + y * 18));
            }
        }

        for (int i = 0; i < 9; ++i) {
            addSlotToContainer(new Slot(player.inventory, i, 8 + i * 18, inventoryBaseY + 58));
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return inventory.isUseableByPlayer(player);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slot) {
        ItemStack stack = null;
        final Slot invSlot = (Slot) inventorySlots.get(slot);
        if (invSlot != null && invSlot.getHasStack()) {
            ItemStack slotStack = invSlot.getStack();
            stack = slotStack.copy();
            if (slot < 1) {
                if (!mergeItemStack(slotStack, 1, inventorySlots.size(), true)) {
                    return null;
                }
            } else if (!mergeItemStack(slotStack, 0, 1, false)) {
                return null;
            }

            if (slotStack.stackSize == 0) {
                invSlot.putStack(null);
            } else {
                invSlot.onSlotChanged();
            }
        }
        return stack;
    }
}
