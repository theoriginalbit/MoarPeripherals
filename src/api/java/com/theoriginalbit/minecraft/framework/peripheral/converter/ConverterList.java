package com.theoriginalbit.minecraft.framework.peripheral.converter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
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
 * Converts a {@link java.util.List} to/from Lua
 *
 * @author theoriginalbit
 */
public class ConverterList implements ITypeConverter {
	@Override
	public Object fromLua(Object obj, Class<?> expected) {
		if (obj instanceof Map && expected == List.class) {
			Map<?, ?> map = (Map<?, ?>) obj;
			if (map.isEmpty()) {
				return ImmutableList.of();
			}
			
			int indexMin = Integer.MAX_VALUE;
			int indexMax = Integer.MIN_VALUE;
			
			Map<Integer, Object> tmp = Maps.newHashMap();
			for (Entry<?, ?> e : map.entrySet()) {
				Object k = e.getKey();
				if (!(k instanceof Number)) {
					return null;
				}
				int index = ((Number) k).intValue();
				if (index < indexMin) { indexMin = index; }
				if (index > indexMax) { indexMax = index; }
				tmp.put(index, e.getValue());
			}
			int size = indexMax - indexMin + 1;
			if (size != tmp.size() || (indexMin != 0 && indexMin != 1)) {
				return null;
			}
			List<Object> result = Lists.newArrayList();
			for (int index = indexMin; index <= indexMax; ++index) {
				Object o = tmp.get(index);
				result.add(o);
			}
			return result;
		}
		return null;
	}
	
	@Override
	public Object toLua(Object obj) {
		if (obj instanceof List) {
			HashMap<Integer, Object> map = Maps.newHashMap();
			List<?> objList = (List<?>) obj;
			for (int i = 0; i < objList.size(); ++i) {
				map.put(i + 1, LuaType.toLua(objList.get(i)));
			}
			return map;
		}
		return null;
	}
}