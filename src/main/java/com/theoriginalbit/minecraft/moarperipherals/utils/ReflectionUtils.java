package com.theoriginalbit.minecraft.moarperipherals.utils;

import java.lang.reflect.Method;

/**
 * A Minecraft mod that adds more peripherals into the ComputerCraft mod.
 * Official Thread:
 * http://www.computercraft.info/forums2/index.php?/topic/19357-
 * Official Wiki:
 * http://wiki.theoriginalbit.com/moarperipherals/
 *
 * Copyright (C) 2014  Joshua Asbury (@theoriginalbit)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/.
 */
public final class ReflectionUtils {

    public static Class<?> getClass(String className) {
        if (className == null || className.isEmpty()) {
            return null;
        }
        try {
            return Class.forName(className);
        } catch (Exception e) {
        }
        return null;
    }

    public static Method getMethod(Class<?> instance, String method, Class<?>... parameters) {
        if (instance != null && method != null) {
            try {
                return instance.getMethod(method, parameters);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static Object callMethod(Object instance, Method method, Object... parameters) {
        if (method != null) {
            try {
                return method.invoke(instance, parameters);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}