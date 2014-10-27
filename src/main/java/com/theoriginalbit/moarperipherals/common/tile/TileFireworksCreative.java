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
import net.minecraft.item.ItemStack;

/**
 * @author theoriginalbit
 * @since 13/10/2014
 */
@LuaPeripheral("firework_launcher")
public class TileFireworksCreative extends TileFireworks {

    public TileFireworksCreative() {
        super(0);
    }

    /*
     * We don't want the buffers returned on the creative launcher, the player shouldn't get free resources
     */
    @Override
    public void onBreak(int x, int y, int z) {
        // NO-OP
    }

    /*
     * We have no inventory
     */
    @Override
    @LuaFunction(name = "getStackInSlot")
    public ItemStack stackInSlot(int slot) {
        return null;
    }

    @Override
    @LuaFunction
    public boolean isCreativeLauncher() {
        return true;
    }

    @LuaFunction(isMultiReturn = true)
    public Object[] load(int slot) {
        return new Object[]{false, "Cannot invoke load on creative launcher"};
    }

    /*
     * This is creative, don't drop the items, just destroy them
     */
    @Override
    @LuaFunction(isMultiReturn = true)
    public Object[] unloadFireworkRocket(int id) {
        if (!bufferRocket.containsItemStackWithId(id)) {
            return new Object[]{"No Firework Rocket with that ID found"};
        }
        bufferRocket.getNextItemStack();
        return new Object[]{true};
    }

    /*
     * This is creative, don't drop the items, just destroy them
     */
    @Override
    @LuaFunction(isMultiReturn = true)
    public Object[] unloadFireworkStar(int id) {
        if (!bufferStar.containsItemStackWithId(id)) {
            return new Object[]{"No Firework Star with that ID found"};
        }
        bufferStar.getNextItemStack();
        return new Object[]{true};
    }

}
