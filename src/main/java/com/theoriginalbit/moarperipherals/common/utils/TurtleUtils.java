/**
 * Copyright 2014-2015 Joshua Asbury (@theoriginalbit)
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
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

/**
 * @author theoriginalbit
 */
public final class TurtleUtils {
    private static final Class<?> CLAZZ_I_TURTLE_TILE = ReflectionUtils.getClass("dan200.computercraft.shared.turtle.blocks.ITurtleTile");
    private static final Method METHOD_GETACCESS = ReflectionUtils.getMethod(CLAZZ_I_TURTLE_TILE, "getAccess");

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
        return tile != null && CLAZZ_I_TURTLE_TILE.isAssignableFrom(tile.getClass());
    }

    public static ITurtleAccess getITurtle(World world, int x, int y, int z) {
        final TileEntity tile = getTileTurtle(world, x, y, z);
        if (tile != null) {
            return (ITurtleAccess) ReflectionUtils.callMethod(tile, METHOD_GETACCESS);
        }
        return null;
    }

    public static TileEntity getTileTurtle(World world, Integer x, Integer y, Integer z) {
        if (x == null || y == null || z == null) {
            return null;
        }
        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile != null && CLAZZ_I_TURTLE_TILE.isAssignableFrom(tile.getClass())) {
            return tile;
        }
        return null;
    }
}
