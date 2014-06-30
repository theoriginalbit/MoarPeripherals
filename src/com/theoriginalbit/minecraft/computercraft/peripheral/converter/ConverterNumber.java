package com.theoriginalbit.minecraft.computercraft.peripheral.converter;

import com.theoriginalbit.minecraft.computercraft.peripheral.interfaces.ITypeConverter;

public class ConverterNumber implements ITypeConverter {
	@Override
	public Object fromLua(Object obj, Class<?> expected) {
		final Double d;
		if (obj instanceof Double) {
			d = (Double) obj;
		} else {
			return null;
		}
		
		if (expected == Double.class || expected == double.class) { return d; }
		if (expected == Integer.class || expected == int.class) { return d.intValue(); }
		if (expected == Float.class || expected == float.class) { return d.floatValue(); }
		if (expected == Long.class || expected == long.class) { return d.longValue(); }
		if (expected == Short.class || expected == short.class) { return d.shortValue(); }
		if (expected == Byte.class || expected == byte.class) { return d.byteValue(); }
		if (expected == Boolean.class || expected == boolean.class) { return d != 0; }
		
		return null;
	}

	@Override
	public Object toLua(Object obj) {
		return obj instanceof Number ? ((Number) obj).doubleValue() : null;
	}
}