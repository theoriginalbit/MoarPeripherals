/**
 * Copyright (c) 2013-2014, Joshua Asbury (@theoriginalbit)
 * http://wiki.theoriginalbit.com/moarperipherals/
 *
 * MoarPeripherals is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://www.mod-buildcraft.com/MMPL-1.0.txt
 */
package com.theoriginalbit.moarperipherals.utils;

import java.lang.reflect.Method;

public final class ReflectionUtils {

    public static Class<?> getClass(String className) {
        if (className == null || className.isEmpty()) {
            return null;
        }
        try {
            return Class.forName(className);
        } catch (Exception ignored) {
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