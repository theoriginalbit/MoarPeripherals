package com.theoriginalbit.minecraft.computercraft.peripheral.converter;

import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.Maps;
import com.theoriginalbit.minecraft.computercraft.peripheral.LuaType;
import com.theoriginalbit.minecraft.computercraft.peripheral.interfaces.ITypeConverter;

public class ConverterMap implements ITypeConverter {
	@Override
	public Object fromLua(Object obj, Class<?> expected) {
		if (obj instanceof Map && expected == Map.class) {
			return obj;
		}
		return null;
	}

	@Override
	public Object toLua(Object obj) {
		if (obj instanceof Map) {
			Map<Object, Object> map = Maps.newHashMap();
			for (Entry<?, ?> e : ((Map<?, ?>) obj).entrySet()) {
				Object k = LuaType.toLua(e.getKey());
				Object v = LuaType.toLua(e.getValue());
				map.put(k, v);
			}
			return map;
		}
		return null;
	}
}