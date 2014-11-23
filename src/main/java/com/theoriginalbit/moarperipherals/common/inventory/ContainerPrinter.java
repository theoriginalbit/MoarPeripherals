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
package com.theoriginalbit.moarperipherals.common.inventory;

import com.theoriginalbit.moarperipherals.common.inventory.abstracts.ContainerMoarP;
import com.theoriginalbit.moarperipherals.common.registry.ModItems;
import com.theoriginalbit.moarperipherals.common.tile.TilePrinter;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/**
 * @author theoriginalbit
 * @since 16/11/14
 */
public class ContainerPrinter extends ContainerMoarP {
    private TilePrinter printer;

    public ContainerPrinter(EntityPlayer player, TilePrinter tile) {
        super(tile);
        printer = tile;

        for (int x = 0; x < 4; ++x) {
            addSlotToContainer(new SlotValidated(printer, x, 26 + (x * 36), 26, new ItemStack(ModItems.itemInkCartridge, 1, x)));
        }

        final ItemStack paper = new ItemStack(Items.paper);
        for (int x = 0; x < 5; ++x) {
            for (int y = 0; y < 2; ++y) {
                addSlotToContainer(new SlotValidated(printer, 4 + (x + y * 5), 66 + x * 18, 57 + y * 18, paper));
            }
        }

        addSlotToContainer(new SlotOutput(printer, 14, 26, 66));

        bindPlayerInventory(player.inventory, 105);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int idx) {
        ItemStack stack = null;
        Slot slot = (Slot) inventorySlots.get(idx);

        if (slot != null && slot.getHasStack()) {
            ItemStack itemStack = slot.getStack();
            stack = itemStack.copy();

            if (idx >= 0 && idx < printer.getSizeInventory()) { // the slot was in the printer
                if (!mergeItemStack(itemStack, printer.getSizeInventory(), inventorySlots.size() - 1)) {
                    return null;
                }
            } else if (isItemInk(itemStack)) { // the slot was in the player's inventory, and was ink
                if (!mergeItemStack(itemStack, itemStack.getItemDamage(), itemStack.getItemDamage() + 1)) {
                    return null;
                }
            } else if (isItemPaper(itemStack)) { // the slot was in the player's inventory, and was paper
                if (!mergeItemStack(itemStack, 4, printer.getSizeInventory() - 1)) {
                    return null;
                }
            } else { // the slot was in the player's inventory, but was something we don't want to accept
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

    private boolean isItemInk(ItemStack stack) {
        return stack.getItem() == ModItems.itemInkCartridge && stack.getItemDamage() != 4;
    }

    private boolean isItemPaper(ItemStack stack) {
        return stack.getItem() == Items.paper;
    }


//
//    @Override
//    public ItemStack transferStackInSlot(EntityPlayer player, int slot) {
//        ItemStack stack = null;
//        final Slot slotInv = (Slot) inventorySlots.get(slot);
//
//        if (slotInv != null && slotInv.getHasStack()) {
//            final ItemStack stackSlot = slotInv.getStack();
//            stack = stackSlot.copy();
//
//            if (slot < playerInv.getSizeInventory()) { // moving from the player inventory
//                if (!mergeItemStack(stackSlot, playerInv.getSizeInventory(), inventorySlots.size() - 1, false)) {
//                    return null;
//                }
//            } else if (!mergeItemStack(stackSlot, 0, playerInv.getSizeInventory() - 1, true)) { // moving from the block
//                return null;
//            }
//
//            if (stackSlot.stackSize == 0) {
//                slotInv.putStack(null);
//            } else {
//                slotInv.onSlotChanged();
//            }
//        }
//
//        return null;
//    }
//
//    {
//        ItemStack stack = null;
//        final Slot invSlot = (Slot) inventorySlots.get(slot);
//
//        if (invSlot != null && invSlot.getHasStack()) {
//            final ItemStack slotStack = invSlot.getStack();
//            stack = slotStack.copy();
//
//            if (slot < playerInv.getSizeInventory()) {
//                if (!mergeItemStack(slotStack, playerInv.getSizeInventory(), inventory.getSizeInventory(), false)) {
//                    return null;
//                }
//            } else if (!mergeItemStack(slotStack, 0, playerInv.getSizeInventory(), true)) {
//                return null;
//            }
//
//            if (slotStack.stackSize == 0) {
//                invSlot.putStack(null);
//            } else {
//                invSlot.onSlotChanged();
//            }
//        }
//
//        return stack;
//    }
}
