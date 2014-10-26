/**
 * Copyright (c) 2013-2014, Joshua Asbury (@theoriginalbit)
 * http://wiki.theoriginalbit.com/moarperipherals/
 *
 * MoarPeripherals is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://www.mod-buildcraft.com/MMPL-1.0.txt
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
