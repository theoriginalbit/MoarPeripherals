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

import com.moarperipherals.init.ModItems;
import com.moarperipherals.tile.TilePrinter;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
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
            final ItemStack stack = new ItemStack(ModItems.itemInkCartridge, 1);
            final int y = 26 + (x * 36);
            addSlotToContainer(new SlotValidated(printer, x, y, 26, stack));
        }

        final ItemStack paper = new ItemStack(Items.paper);
        for (int row = 0; row < 2; ++row) {
            for (int col = 0; col < 5; ++col) {
                final int index = 4 + (col + row * 5);
                final int x = 66 + col * 18;
                final int y = 57 + row * 18;
                addSlotToContainer(new SlotValidated(printer, index, x, y, paper));
            }
        }

        addSlotToContainer(new SlotOutput(printer, 14, 26, 66));

        bindPlayerInventory(player.inventory, 105);
    }
}
