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
package com.theoriginalbit.moarperipherals.common.tile.firework;

import net.minecraft.item.ItemStack;

/**
 * @author theoriginalbit
 * @since 22/10/14
 */
class ItemStackWrapper {
    public static int NEXT_ID = 0;

    private final ItemStack itemStack;
    private final int id;

    public ItemStackWrapper(ItemStack stack) {
        itemStack = stack;
        id = NEXT_ID++;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public int getId() {
        return id;
    }

}
