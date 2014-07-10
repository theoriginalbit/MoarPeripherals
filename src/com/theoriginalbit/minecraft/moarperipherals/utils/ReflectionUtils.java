package com.theoriginalbit.minecraft.moarperipherals.utils;

import java.lang.reflect.Method;

public final class ReflectionUtils {
	public static Class<?> getClass(String className) {
		if (className == null || className.isEmpty()) { return null; }
		try {
			return Class.forName(className);
		} catch (Exception e) {}
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