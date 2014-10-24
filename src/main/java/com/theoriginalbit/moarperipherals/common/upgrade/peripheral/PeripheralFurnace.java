/**
 * Copyright (c) 2013-2014, Joshua Asbury (@theoriginalbit)
 * http://wiki.theoriginalbit.com/moarperipherals/
 *
 * MoarPeripherals is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://www.mod-buildcraft.com/MMPL-1.0.txt
 */
package com.theoriginalbit.moarperipherals.common.upgrade.peripheral;

import com.theoriginalbit.framework.peripheral.annotation.LuaFunction;
import com.theoriginalbit.framework.peripheral.annotation.LuaPeripheral;
import dan200.computercraft.api.turtle.ITurtleAccess;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

/**
 * @author theoriginalbit
 * @since 21/10/14
 */
@LuaPeripheral("furnace")
public class PeripheralFurnace {
    private final ITurtleAccess turtle;
    private final IInventory inv;

    public PeripheralFurnace(ITurtleAccess access) {
        turtle = access;
        inv = turtle.getInventory();
    }

    @LuaFunction(isMultiReturn = true)
    public Object[] smelt(int slot, int amount) {
        // convert from an assumed Lua index to a Java index
        --slot;
        if (slot < 1 || slot > inv.getSizeInventory()) {
            return new Object[]{false, "expected slot between 1 and " + inv.getSizeInventory()};
        }
        final ItemStack stack = inv.getStackInSlot(slot);
        if (stack == null) {
            return new Object[]{false, "nothing to smelt"};
        }
        amount = Math.min(stack.stackSize, amount);

        return new Object[]{true};
    }

}
