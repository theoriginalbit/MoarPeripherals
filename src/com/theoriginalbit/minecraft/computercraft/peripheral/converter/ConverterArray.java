package com.theoriginalbit.minecraft.computercraft.peripheral.converter;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.Maps;
import com.theoriginalbit.minecraft.computercraft.peripheral.LuaType;
import com.theoriginalbit.minecraft.computercraft.peripheral.interfaces.ITypeConverter;

public class ConverterArray implements ITypeConverter {
	@Override
	public Object fromLua(Object obj, Class<?> expected) {
		if (obj instanceof Map && expected.isArray()) {
			Map<?, ?> map = (Map<?, ?>) obj;
			Class<?> component = expected.getComponentType();
			if (map.isEmpty()) {
				return Array.newInstance(component, 0);
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
			Object result = Array.newInstance(component, size);
			for (int i = 0, index = indexMin; i < size; ++i, ++index) {
				Object in = tmp.get(index);
				Object out = LuaType.fromLua(in, component);
				if (out == null) {
					return null;
				}
				Array.set(result, i, out);
			}
			return result;
		}
		return null;
	}
	
	@Override
	public Object toLua(Object obj) {
		if (obj.getClass().isArray()) {
			HashMap<Integer, Object> map = Maps.newHashMap();
			int length = Array.getLength(obj);
			for (int i = 0; i < length; ++i) {
				map.put(i + 1, LuaType.toLua(Array.get(0, i)));
			}
			return map;
		}
		return null;
	}
}