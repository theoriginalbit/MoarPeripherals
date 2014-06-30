package com.theoriginalbit.minecraft.computercraft.peripheral.converter;

import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import com.theoriginalbit.minecraft.computercraft.peripheral.interfaces.ITypeConverter;

public class ConverterDefault implements ITypeConverter {
	@Override
	public Object fromLua(Object obj, Class<?> expected) {
		if (compareTypes(obj.getClass(), expected)) {
			return obj;
		}
		return null;
	}

	@Override
	public Object toLua(Object obj) {
		if (obj instanceof Boolean) {
			return obj;
		}
		return null;
	}
	
	private static final BiMap<Class<?>, Class<?>> WRAPPERS = ImmutableBiMap.<Class<?>, Class<?>> builder()
			.put(long.class, Long.class)
			.put(int.class, Integer.class)
			.put(short.class, Short.class)
			.put(byte.class, Byte.class)
			.put(boolean.class, Boolean.class)
			.put(double.class, Double.class)
			.put(float.class, Float.class)
			.put(char.class, Character.class)
			.build();
	
	private static boolean compareTypes(Class<?> left, Class<?> right) {
		if (left.isPrimitive()) left = WRAPPERS.get(left);
		if (right.isPrimitive()) right = WRAPPERS.get(right);
		return left.equals(right);
	}
	
}