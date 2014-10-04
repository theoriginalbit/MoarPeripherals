/**
 * Copyright (c) 2013-2014, Joshua Asbury (@theoriginalbit)
 * http://wiki.theoriginalbit.com/moarperipherals/
 *
 * MoarPeripherals is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://www.mod-buildcraft.com/MMPL-1.0.txt
 */
package com.theoriginalbit.minecraft.moarperipherals.common.utils;

import java.lang.reflect.Method;

public final class NEIUtils {

    private static final Class<?> CLAZZ_API = ReflectionUtils.getClass("codechicken.nei.api.API");
    private static final Method METHOD_HIDEITEM = ReflectionUtils.getMethod(CLAZZ_API, "hideItem", new Class[]{int.class});

    public static void hideFromNEI(int id) {
        if (CLAZZ_API != null) {
            if (METHOD_HIDEITEM != null) {
                ReflectionUtils.callMethod(CLAZZ_API, METHOD_HIDEITEM, id);
            }
        }
    }

}