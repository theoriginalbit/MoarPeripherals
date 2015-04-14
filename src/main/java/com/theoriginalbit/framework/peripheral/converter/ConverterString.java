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