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
package com.theoriginalbit.moarperipherals.common.utils;

import dan200.computercraft.api.turtle.ITurtleAccess;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import java.lang.reflect.Method;

public final class ComputerUtils {

    private static final Class<?> CLAZZ_TILECOMPUTERBASE = ReflectionUtils.getClass("dan200.computercraft.shared.computer.blocks.TileComputerBase");
    private static final Method METHOD_GETCOMPUTER = ReflectionUtils.getMethod(CLAZZ_TILECOMPUTERBASE, "getComputer");

    private static final Class<?> CLAZZ_ICOMPUTER = ReflectionUtils.getClass("dan200.computercraft.shared.computer.core.IComputer");
    private static final Method METHOD_QUEUEEVENT = ReflectionUtils.getMethod(CLAZZ_ICOMPUTER, "queueEvent", String.class, Object[].class);
    private static final Method METHOD_ISON = ReflectionUtils.getMethod(CLAZZ_ICOMPUTER, "isOn");
    private static final Method METHOD_TURNON = ReflectionUtils.getMethod(CLAZZ_ICOMPUTER, "turnOn");
    private static final Method METHOD_SHUTDOWN = ReflectionUtils.getMethod(CLAZZ_ICOMPUTER, "shutdown");
    private static final Method METHOD_REBOOT = ReflectionUtils.getMethod(CLAZZ_ICOMPUTER, "reboot");

    private static final Class<?> CLAZZ_ITURTLETILE = ReflectionUtils.getClass("dan200.computercraft.shared.turtle.blocks.ITurtleTile");
    private static final Method METHOD_GETACCESS = ReflectionUtils.getMethod(CLAZZ_ITURTLETILE, "getAccess");

    private static final String EVENT_TERMINATE = "terminate";

    public static TileEntity getTileComputerBase(World world, Integer x, Integer y, Integer z) {
        if (x == null || y == null || z == null) {
            return null;
        }
        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile != null && CLAZZ_TILECOMPUTERBASE.isAssignableFrom(tile.getClass())) {
            return tile;
        }
        return null;
    }

    public static TileEntity getTileTurtle(World world, Integer x, Integer y, Integer z) {
        if (x == null || y == null || z == null) {
            return null;
        }
        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile != null && CLAZZ_ITURTLETILE.isAssignableFrom(tile.getClass())) {
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

    public static ITurtleAccess getITurtle(World world, int x, int y, int z) {
        final TileEntity tile = getTileTurtle(world, x, y, z);
        if (tile != null) {
            return (ITurtleAccess) ReflectionUtils.callMethod(tile, METHOD_GETACCESS);
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
        Object computer = getIComputer(tile.getWorldObj(), tile.xCoord, tile.yCoord, tile.zCoord);
        if (computer != null && CLAZZ_ICOMPUTER.isAssignableFrom(computer.getClass())) {
            ReflectionUtils.callMethod(computer, METHOD_QUEUEEVENT, event, args);
        }
    }

    public static boolean isOn(TileEntity tile) {
        if (tile == null) {
            return false;
        }
        Object computer = getIComputer(tile.getWorldObj(), tile.xCoord, tile.yCoord, tile.zCoord);
        if (computer != null && CLAZZ_ICOMPUTER.isAssignableFrom(computer.getClass())) {
            return (Boolean) ReflectionUtils.callMethod(computer, METHOD_ISON);
        }
        return false;
    }

    public static void turnOn(TileEntity tile) {
        if (tile == null) {
            return;
        }
        Object computer = getIComputer(tile.getWorldObj(), tile.xCoord, tile.yCoord, tile.zCoord);
        if (computer != null && CLAZZ_ICOMPUTER.isAssignableFrom(computer.getClass())) {
            ReflectionUtils.callMethod(computer, METHOD_TURNON);
        }
    }

    public static void shutdown(TileEntity tile) {
        if (tile == null) {
            return;
        }
        Object computer = getIComputer(tile.getWorldObj(), tile.xCoord, tile.yCoord, tile.zCoord);
        if (computer != null && CLAZZ_ICOMPUTER.isAssignableFrom(computer.getClass())) {
            ReflectionUtils.callMethod(computer, METHOD_SHUTDOWN);
        }
    }

    public static void reboot(TileEntity tile) {
        if (tile == null) {
            return;
        }
        Object computer = getIComputer(tile.getWorldObj(), tile.xCoord, tile.yCoord, tile.zCoord);
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

    public static ITurtleAccess findTurtle(World world, int x, int y, int z) {
        if (isTurtle(world, x + 1, y, z)) return getITurtle(world, x + 1, y, z);
        if (isTurtle(world, x, y + 1, z)) return getITurtle(world, x, y + 1, z);
        if (isTurtle(world, x, y, z + 1)) return getITurtle(world, x, y, z + 1);
        if (isTurtle(world, x - 1, y, z)) return getITurtle(world, x - 1, y, z);
        if (isTurtle(world, x, y - 1, z)) return getITurtle(world, x, y - 1, z);
        if (isTurtle(world, x, y, z - 1)) return getITurtle(world, x, y, z - 1);
        return null;
    }

    public static boolean isTurtle(World world, int x, int y, int z) {
        final TileEntity tile = world.getTileEntity(x, y, z);
        return tile != null && CLAZZ_ITURTLETILE.isAssignableFrom(tile.getClass());
    }

}