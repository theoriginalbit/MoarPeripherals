/**
 * Copyright (c) 2013-2014, Joshua Asbury (@theoriginalbit)
 * http://wiki.theoriginalbit.com/moarperipherals/
 *
 * MoarPeripherals is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://www.mod-buildcraft.com/MMPL-1.0.txt
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
