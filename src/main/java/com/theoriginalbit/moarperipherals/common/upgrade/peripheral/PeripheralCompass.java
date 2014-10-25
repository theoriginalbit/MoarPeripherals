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
import net.minecraftforge.common.util.ForgeDirection;

/**
 * @author theoriginalbit
 * @since 25/10/14
 */
@LuaPeripheral("compass")
public class PeripheralCompass {
    private final ITurtleAccess turtle;

    public PeripheralCompass(ITurtleAccess access) {
        turtle = access;
    }

    @LuaFunction
    public String getFacing() {
        return ForgeDirection.getOrientation(turtle.getDirection()).name().toLowerCase();
    }
}
