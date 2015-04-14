/**
 * Copyright 2014 Joshua Asbury (@theoriginalbit)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.theoriginalbit.framework.peripheral.converter;

import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;

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