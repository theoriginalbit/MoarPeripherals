/**
 * Copyright (c) 2013-2014, Joshua Asbury (@theoriginalbit)
 * http://wiki.theoriginalbit.com/moarperipherals/
 *
 * MoarPeripherals is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://www.mod-buildcraft.com/MMPL-1.0.txt
 */
package com.theoriginalbit.minecraft.moarperipherals.common.utils;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import java.lang.reflect.Method;

public final class ComputerUtils {

    private static final Class<?> CLAZZ_TILECOMPUTERBASE = ReflectionUtils.getClass("dan200.computercraft.shared.computer.blocks.TileComputerBase");
    private static final Method METHOD_GETCOMPUTER = ReflectionUtils.getMethod(CLAZZ_TILECOMPUTERBASE, "getComputer", new Class[0]);

    private static final Class<?> CLAZZ_ICOMPUTER = ReflectionUtils.getClass("dan200.computercraft.shared.computer.core.IComputer");
    private static final Method METHOD_QUEUEEVENT = ReflectionUtils.getMethod(CLAZZ_ICOMPUTER, "queueEvent", new Class[]{String.class, Object[].class});
    private static final Method METHOD_ISON = ReflectionUtils.getMethod(CLAZZ_ICOMPUTER, "isOn", new Class[]{});
    private static final Method METHOD_TURNON = ReflectionUtils.getMethod(CLAZZ_ICOMPUTER, "turnOn", new Class[]{});
    private static final Method METHOD_SHUTDOWN = ReflectionUtils.getMethod(CLAZZ_ICOMPUTER, "shutdown", new Class[]{});
    private static final Method METHOD_REBOOT = ReflectionUtils.getMethod(CLAZZ_ICOMPUTER, "reboot", new Class[]{});

    private static final String EVENT_TERMINATE = "terminate";

    public static TileEntity getTileComputerBase(World world, Integer x, Integer y, Integer z) {
        if (x == null || y == null || z == null) {
            return null;
        }
        TileEntity tile = world.getBlockTileEntity(x, y, z);
        if (tile != null && CLAZZ_TILECOMPUTERBASE.isAssignableFrom(tile.getClass())) {
            return tile;
        }
        return null;
    }

    public static Object getIComputer(World world, int x, int y, int z) {
        TileEntity tile = getTileComputerBase(world, x, y, z);
        if (tile != null) {
            return ReflectionUtils.callMethod(tile, METHOD_GETCOMPUTER);
        }
        return null;
    }

    public static boolean isTileComputer(TileEntity tile) {
        return tile != null && CLAZZ_TILECOMPUTERBASE.isAssignableFrom(tile.getClass());
    }

    public static void queueEvent(TileEntity tile, String event, Object... args) {
        if (tile == null) {
            return;
        }
        Object computer = getIComputer(tile.worldObj, tile.xCoord, tile.yCoord, tile.zCoord);
        if (computer != null && CLAZZ_ICOMPUTER.isAssignableFrom(computer.getClass())) {
            ReflectionUtils.callMethod(computer, METHOD_QUEUEEVENT, event, args);
        }
    }

    public static boolean isOn(TileEntity tile) {
        if (tile == null) {
            return false;
        }
        Object computer = getIComputer(tile.worldObj, tile.xCoord, tile.yCoord, tile.zCoord);
        if (computer != null && CLAZZ_ICOMPUTER.isAssignableFrom(computer.getClass())) {
            return (Boolean) ReflectionUtils.callMethod(computer, METHOD_ISON);
        }
        return false;
    }

    public static void turnOn(TileEntity tile) {
        if (tile == null) {
            return;
        }
        Object computer = getIComputer(tile.worldObj, tile.xCoord, tile.yCoord, tile.zCoord);
        if (computer != null && CLAZZ_ICOMPUTER.isAssignableFrom(computer.getClass())) {
            ReflectionUtils.callMethod(computer, METHOD_TURNON);
        }
    }

    public static void shutdown(TileEntity tile) {
        if (tile == null) {
            return;
        }
        Object computer = getIComputer(tile.worldObj, tile.xCoord, tile.yCoord, tile.zCoord);
        if (computer != null && CLAZZ_ICOMPUTER.isAssignableFrom(computer.getClass())) {
            ReflectionUtils.callMethod(computer, METHOD_SHUTDOWN);
        }
    }

    public static void reboot(TileEntity tile) {
        if (tile == null) {
            return;
        }
        Object computer = getIComputer(tile.worldObj, tile.xCoord, tile.yCoord, tile.zCoord);
        if (computer != null && CLAZZ_ICOMPUTER.isAssignableFrom(computer.getClass())) {
            ReflectionUtils.callMethod(computer, METHOD_REBOOT);
        }
    }

    public static void terminate(TileEntity tile) {
        if (tile == null) {
            return;
        }
        queueEvent(tile, EVENT_TERMINATE);
    }

}