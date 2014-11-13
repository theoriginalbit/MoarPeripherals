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
package com.theoriginalbit.moarperipherals.common.block.container;

import com.theoriginalbit.moarperipherals.common.block.abstracts.ContainerCommon;
import com.theoriginalbit.moarperipherals.common.block.container.slot.SlotReadOnly;
import com.theoriginalbit.moarperipherals.common.tile.TileComputerCrafter;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
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

    @Override
    public ItemStack slotClick(int slot, int x, int y, EntityPlayer player) {
        if (!player.worldObj.isRemote && slot == craftResult.slotNumber) {
            try {
                crafter.doCraft();
            } catch (Exception ignored) {
            }
            detectAndSendChanges();
        }
        return super.slotClick(slot, x, y, player);
    }

}
