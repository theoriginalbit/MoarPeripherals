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
public class ContainerCrafter extends ContainerMoarP {
    private TileComputerCrafter crafter;
    private SlotReadOnly craftResult;

    public ContainerCrafter(EntityPlayer player, TileComputerCrafter inventory) {
        super(inventory);
        crafter = inventory;

        // the crafting grid
        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 3; ++col) {
                addSlotToContainer(new SlotReadOnly(inventory.craftingInv, col + row * 3, 30 + col * 18, 17 + row * 18));
            }
        }

        // the TE inventory
        for (int row = 0; row < 2; ++row) {
            for (int col = 0; col < 9; ++col) {
                addSlotToContainer(new Slot(crafter, col + row * 9, 8 + col * 18, 90 + row * 18));
            }
        }

        // the fake output slot is so fake that it's not even stored in the TE
        addSlotToContainer(craftResult = new SlotReadOnly(new InventoryBasic("", false, 1), 0, 124, 35));

        bindPlayerInventory(player.inventory, 140);
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
