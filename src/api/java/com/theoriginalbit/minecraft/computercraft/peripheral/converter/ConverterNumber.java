package com.theoriginalbit.minecraft.computercraft.peripheral.converter;

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
 * Converts a number to/from Lua
 *
 * @author theoriginalbit
 */
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