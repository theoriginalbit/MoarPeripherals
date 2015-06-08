/**
 * Copyright 2014-2015 Joshua Asbury (@theoriginalbit)
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.moarperipherals.integration.converter;

import com.moarperipherals.tile.TileInteractiveSorter.Side;
import com.theoriginalbit.framework.peripheral.converter.ITypeConverter;
import dan200.computercraft.api.lua.LuaException;

/**
 * @author Joshua Asbury (@theoriginalbit)
 */
public class ConverterSide implements ITypeConverter {
    @Override
    public Object fromLua(Object obj, Class<?> expected) throws LuaException {
        if (expected == Side.class && obj instanceof String) {
            return Side.valueOf(((String) obj).toUpperCase());
        }
        return null;
    }

    @Override
    public Object toLua(Object obj) throws LuaException {
        if (obj instanceof Side) {
            return ((Side) obj).name();
        }
        return null;
    }
}
