package com.theoriginalbit.minecraft.computercraft.peripheral.converter;

import com.theoriginalbit.minecraft.computercraft.peripheral.interfaces.ITypeConverter;

public class ConverterString implements ITypeConverter {
	@Override
	public Object fromLua(Object obj, Class<?> expected) {
		if (expected == String.class) {
			return obj.toString();
		}
		return null;
	}

	@Override
	public Object toLua(Object obj) {
		return obj.toString(); // catch-all
	}
}