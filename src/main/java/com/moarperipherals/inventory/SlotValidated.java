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

import com.google.common.collect.Lists;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;

/**
 * @author theoriginalbit
 * @since 16/11/14
 */
public class SlotValidated extends Slot {
    private final ArrayList<ItemStack> allowedItems;

    public SlotValidated(IInventory inv, int index, int displayX, int displayY, ItemStack... stacks) {
        super(inv, index, displayX, displayY);
        allowedItems = Lists.newArrayList(stacks);
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        for (final ItemStack itemStack : allowedItems) {
            if (stack.isItemEqual(itemStack)) {
                return true;
            }
        }
        return false;
    }
}
