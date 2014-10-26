/**
 * Copyright (c) 2013-2014, Joshua Asbury (@theoriginalbit)
 * http://wiki.theoriginalbit.com/moarperipherals/
 *
 * MoarPeripherals is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://www.mod-buildcraft.com/MMPL-1.0.txt
 */
package com.theoriginalbit.moarperipherals.common.block.container;

import com.theoriginalbit.moarperipherals.common.block.abstracts.ContainerCommon;
import com.theoriginalbit.moarperipherals.common.block.container.slot.SlotReadOnly;
import com.theoriginalbit.moarperipherals.common.tile.TileComputerCrafter;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.inventory.Slot;
import net.minecraft.item.crafting.CraftingManager;

/**
 * @author theoriginalbit
 * @since 26/10/14
 */
public class ContainerCrafter extends ContainerCommon {
    private TileComputerCrafter crafter;
    private SlotReadOnly craftResult;

    public ContainerCrafter(EntityPlayer player, TileComputerCrafter inventory) {
        super(player, inventory, 140);
        crafter = inventory;

        for (int x = 0; x < 3; ++x) {
            for (int y = 0; y < 3; ++y) {
                addSlotToContainer(new SlotReadOnly(inventory.craftingInv, x + y * 3, 30 + x * 18, 17 + y * 18));
            }
        }

        addSlotToContainer(craftResult = new SlotReadOnly(new InventoryBasic("", false, 1), 0, 124, 35));

        for (int x = 0; x < 9; ++x) {
            for (int y = 0; y < 2; ++y) {
                addSlotToContainer(new Slot(crafter, x + y * 9, 8 + x * 18, 90 + y * 18));
            }
        }
    }

    @Override
    public void detectAndSendChanges() {
        craftResult.putStack(CraftingManager.getInstance().findMatchingRecipe(crafter.craftingInv, crafter.getWorldObj()));
        super.detectAndSendChanges();
    }

}
