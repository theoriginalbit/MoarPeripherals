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
 * Converts a String to/from Lua, it also acts as a catch-all,
 * converting anything that hasn't been converted, as such
 * this is the last conversion to happen
 *
 * @author theoriginalbit
 */
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