/**
 * Copyright (c) 2013-2014, Joshua Asbury (@theoriginalbit)
 * http://wiki.theoriginalbit.com/moarperipherals/
 *
 * MoarPeripherals is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://www.mod-buildcraft.com/MMPL-1.0.txt
 */
package com.theoriginalbit.moarperipherals.common.tile;

import com.theoriginalbit.framework.peripheral.annotation.LuaFunction;
import com.theoriginalbit.framework.peripheral.annotation.LuaPeripheral;
import com.theoriginalbit.moarperipherals.common.reference.Constants;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFirework;
import net.minecraft.item.ItemFireworkCharge;
import net.minecraft.item.ItemStack;

/**
 * @author theoriginalbit
 * @since 13/10/2014
 */
@LuaPeripheral("firework_launcher")
public class TileFireworksCreative extends TileFireworks {

    @Override
    public String getInventoryName() {
        return Constants.GUI.FIREWORKS_CREATIVE.getLocalised();
    }

    /*
     * This is creative, we don't need to accept items, but sure,
     * lets accept only fireworks that can be loaded in
     */
    @Override
    public boolean isItemValidForSlot(int slot, ItemStack stack) {
        final Item item = stack.getItem();
        return item instanceof ItemFirework || item instanceof ItemFireworkCharge;
    }

    /*
     * This is creative, don't drop the items, just destroy them
     */
    @LuaFunction(isMultiReturn = true)
    public Object[] cancelLaunch() {
        if (!canLaunch()) {
            return new Object[]{false, "no launches to cancel"};
        }
        // remove the next rocket from the buffer, but in a way they don't get it
        bufferRocket.getNextItemStack();
        return new Object[]{true};
    }

    /*
     * This is creative, we always have everything!
     */
    @Override
    protected int findQtyOf(ItemStack template) {
        return 64;
    }

    /*
     * This is creative, lets just dupe the item
     */
    @Override
    protected ItemStack extract(ItemStack template) {
        final ItemStack stack = template.copy();
        stack.stackSize = 1;
        return stack;
    }

}
