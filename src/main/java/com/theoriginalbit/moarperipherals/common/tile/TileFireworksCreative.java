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
import net.minecraft.item.ItemFirework;
import net.minecraft.item.ItemStack;

/**
 * @author theoriginalbit
 * @since 13/10/2014
 */
@LuaPeripheral("firework_launcher")
public class TileFireworksCreative  extends TileFireworks {

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
        return stack.getItem() instanceof ItemFirework;
    }

    /*
     * This is creative, don't drop the items, just destroy them
     */
    @LuaFunction(isMultiReturn = true)
    public Object[] cancelNextLaunch() {
        if (bufferRocket.getCurrentSize() > 0) {
            bufferRocket.getNextItemStack();
            return new Object[]{true};
        }
        return new Object[]{false, "no launches to cancel"};
    }

    /*
     * This is creative, don't drop the items, just destroy them
     */
    @LuaFunction(isMultiReturn = true)
    public Object[] cancelAllLaunches() {
        if (bufferRocket.getCurrentSize() > 0) {
            bufferRocket.clear();
            return new Object[]{true};
        }
        return new Object[]{false, "no launches to cancel"};
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
