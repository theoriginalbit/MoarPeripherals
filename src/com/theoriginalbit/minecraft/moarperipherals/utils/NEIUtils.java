package com.theoriginalbit.minecraft.moarperipherals.utils;

import java.lang.reflect.Method;

public final class NEIUtils {
	private static final Class<?> CLAZZ_API = ReflectionUtils.getClass("codechicken.nei.api.API");
	private static final Method METHOD_HIDEITEM = ReflectionUtils.getMethod(CLAZZ_API, "hideItem", new Class[]{ int.class });
	
	public static final void hideFromNEI(int id) {
		if (CLAZZ_API != null) {
			if (METHOD_HIDEITEM != null) {
				ReflectionUtils.callMethod(CLAZZ_API, METHOD_HIDEITEM, id);
			}
		}
	}
}