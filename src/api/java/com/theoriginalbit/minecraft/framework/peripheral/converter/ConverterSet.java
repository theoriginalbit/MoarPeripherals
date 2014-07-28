package com.theoriginalbit.minecraft.framework.peripheral.converter;

import java.util.Map;
import java.util.Set;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.theoriginalbit.minecraft.framework.peripheral.LuaType;

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
 * Converts a {@link java.util.Set} to/from Lua
 *
 * @author theoriginalbit
 */
public class ConverterSet implements ITypeConverter {
	@Override
	public Object fromLua(Object obj, Class<?> expected) {
		if (obj instanceof Map && expected == Set.class) {
			return Sets.newHashSet(((Map<?, ?>) obj).keySet());
		}
		return null;
	}

	@Override
	public Object toLua(Object obj) {
		if (obj instanceof Set) {
			Map<Object, Boolean> result = Maps.newHashMap();
			for (Object o : (Set<?>) obj) {
				result.put(LuaType.toLua(o), true);
			}
			return result;
		}
		return null;
	}
}