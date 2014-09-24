/**
 * Copyright (c) 2013-2014, Joshua Asbury (@theoriginalbit)
 * http://wiki.theoriginalbit.com/moarperipherals/
 *
 * MoarPeripherals is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://www.mod-buildcraft.com/MMPL-1.0.txt
 */
package com.theoriginalbit.moarperipherals.utils;

import dan200.computercraft.api.peripheral.IPeripheral;
import net.minecraft.tileentity.TileEntity;

import java.lang.reflect.Method;

public final class PeripheralUtils {

    private static final Class<?> CLAZZ_TILEMODEMBASE = ReflectionUtils.getClass("dan200.computercraft.shared.peripheral.modem.TileModemBase");
    private static final Method METHOD_GETPERIPHERAL = ReflectionUtils.getMethod(CLAZZ_TILEMODEMBASE, "getPeripheral", new Class[]{int.class});

    public static IPeripheral getIPeripheral(TileEntity tile) {
        if (tile != null && CLAZZ_TILEMODEMBASE.isAssignableFrom(tile.getClass())) {
            for (int i = 0; i < 6; ++i) {
                Object obj = ReflectionUtils.callMethod(tile, METHOD_GETPERIPHERAL, i);
                if (obj != null && obj instanceof IPeripheral) {
                    return (IPeripheral) obj;
                }
            }
        }
        return null;
    }

}
