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

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.theoriginalbit.framework.peripheral.LuaType;
import dan200.computercraft.api.lua.LuaException;

import java.util.Map;
import java.util.Set;

/**
 * Converts a {@link java.util.Set} to/from Lua
 *
 * @author theoriginalbit
 */
public class ConverterSet implements ITypeConverter {
    @Override
    public Object fromLua(Object obj, Class<?> expected) throws LuaException {
        if (obj instanceof Map && expected == Set.class) {
            return Sets.newHashSet(((Map<?, ?>) obj).keySet());
        }
        return null;
    }

    @Override
    public Object toLua(Object obj) throws LuaException {
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