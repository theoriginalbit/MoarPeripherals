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
package com.theoriginalbit.moarperipherals.common.inventory;

import com.theoriginalbit.moarperipherals.common.base.ContainerMoarP;
import com.theoriginalbit.moarperipherals.common.init.ModItems;
import com.theoriginalbit.moarperipherals.common.tile.TilePrinter;
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
            addSlotToContainer(new SlotValidated(printer, x, 26 + (x * 36), 26, new ItemStack(ModItems.itemInkCartridge, 1, x)));
        }

        final ItemStack paper = new ItemStack(Items.paper);
        for (int row = 0; row < 2; ++row) {
            for (int col = 0; col < 5; ++col) {
                addSlotToContainer(new SlotValidated(printer, 4 + (col + row * 5), 66 + col * 18, 57 + row * 18, paper));
            }
        }

        addSlotToContainer(new SlotOutput(printer, 14, 26, 66));

        bindPlayerInventory(player.inventory, 105);
    }
}
