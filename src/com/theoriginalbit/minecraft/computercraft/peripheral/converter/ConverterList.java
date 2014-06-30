package com.theoriginalbit.minecraft.computercraft.peripheral.converter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.theoriginalbit.minecraft.computercraft.peripheral.LuaType;
import com.theoriginalbit.minecraft.computercraft.peripheral.interfaces.ITypeConverter;

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