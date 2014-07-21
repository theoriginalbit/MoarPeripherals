package com.theoriginalbit.minecraft.computercraft.peripheral.converter;

import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;

/**
 * Peripheral Framework is an open-source framework that has the aim of
 * allowing developers to implement their ComputerCraft peripherals faster,
 * easier, and cleaner; allowing them to focus more on developing their
 * content.
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

/**
 * Converts primitive wrappers to/from Lua
 *
 * @author theoriginalbit
 */
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