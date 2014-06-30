package com.theoriginalbit.minecraft.computercraft.peripheral.converter;

import java.util.Map;
import java.util.Set;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.theoriginalbit.minecraft.computercraft.peripheral.LuaType;
import com.theoriginalbit.minecraft.computercraft.peripheral.interfaces.ITypeConverter;

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