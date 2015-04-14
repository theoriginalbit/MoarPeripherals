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
package com.theoriginalbit.framework.peripheral;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.theoriginalbit.framework.peripheral.converter.*;
import dan200.computercraft.api.lua.ILuaObject;
import dan200.computercraft.api.lua.LuaException;

import java.util.*;

/**
 * An Enum to specify the mapping between Java classes and Lua types
 * and perform data type conversion between both languages.
 * <p/>
 * If you have another ITypeConverter to register with this system
 * you can do it in one of your mods initialisation phases. See
 * the registerTypeConverter method for more information.
 * <p/>
 * IMPORTANT:
 * This is a backend class, you should never need to use this, and
 * modifying this may have unexpected results.
 *
 * @author theoriginalbit
 */
public final class LuaType {

    private static final Deque<ITypeConverter> CONVERTERS = Lists.newLinkedList();
    private static final HashMap<Class<?>, String> CLASS_TO_NAME = Maps.newHashMap();

    /**
     * Registers the supplied converter to the conversion system.
     * Use this to supply converters for custom classes, such as
     * ItemStack or the likes, so that these objects can be converted
     * to and from Lua.
     * <p/>
     * NOTE: it adds it to the start because order is important!
     */
    public static void registerTypeConverter(ITypeConverter converter) {
        if (!CONVERTERS.contains(converter)) {
            CONVERTERS.addFirst(converter);
        }
    }

    /**
     * Maps the supplied Java class to the supplied Lua name, for example
     * an array, map, list, and set, are all mapped to "table" so that when
     * these arguments are supplied incorrectly the user will be told
     * 'expected table, got string'. If there is no mapping '?' will be
     * returned, as such it is highly recommended that you register a
     * readable name for any custom converters you make.
     */
    public static void registerClassToNameMapping(Class<?> clazz, String name) {
        if (!CLASS_TO_NAME.containsKey(clazz) && !CLASS_TO_NAME.containsValue(name)) {
            CLASS_TO_NAME.put(clazz, name);
        }
    }

    /**
     * Returns the Lua name for the supplied Java class if a mapping exists for it
     * if a mapping does not exist '?' will be returned.
     */
    public static String getLuaName(Class<?> clazz) {
        if (clazz == null) {
            return "nil";
        }

        if (CLASS_TO_NAME.containsKey(clazz)) {
            return CLASS_TO_NAME.get(clazz);
        }

        for (Map.Entry<Class<?>, String> entry : CLASS_TO_NAME.entrySet()) {
            if (entry.getKey().isAssignableFrom(clazz) || clazz.isAssignableFrom(entry.getKey())) {
                return entry.getValue();
            }
        }

        return "?";
    }

    public static Object fromLua(Object obj, Class<?> expected) throws LuaException {
        if (expected == Object.class) {
            return obj;
        }

        for (ITypeConverter converter : CONVERTERS) {
            Object response = converter.fromLua(obj, expected);
            if (response != null) {
                return response;
            }
        }
        return null;
    }

    public static Object toLua(Object obj) throws LuaException {
        if (obj == null || obj instanceof ILuaObject) {
            return obj;
        }

        for (ITypeConverter converter : CONVERTERS) {
            Object response = converter.toLua(obj);
            if (response != null) {
                return response;
            }
        }

        // a catch-all of a catch-all? sure :P
        throw new IllegalStateException("Conversion failed on " + obj);
    }

    static {
        // register default class to name mappings
        CLASS_TO_NAME.put(Object[].class, "table");
        CLASS_TO_NAME.put(Map.class, "table");
        CLASS_TO_NAME.put(List.class, "table");
        CLASS_TO_NAME.put(Set.class, "table");
        CLASS_TO_NAME.put(Number.class, "number");
        CLASS_TO_NAME.put(int.class, "number");
        CLASS_TO_NAME.put(long.class, "number");
        CLASS_TO_NAME.put(short.class, "number");
        CLASS_TO_NAME.put(byte.class, "number");
        CLASS_TO_NAME.put(double.class, "number");
        CLASS_TO_NAME.put(float.class, "number");
        CLASS_TO_NAME.put(String.class, "string");
        CLASS_TO_NAME.put(Void.class, "nil");
        CLASS_TO_NAME.put(Boolean.class, "boolean");
        CLASS_TO_NAME.put(boolean.class, "boolean");
        CLASS_TO_NAME.put(Void.class, "nil");
        CLASS_TO_NAME.put(void.class, "nil");
        CLASS_TO_NAME.put(Object.class, "?");

        // register default converters order is important!
        CONVERTERS.add(new ConverterArray());
        CONVERTERS.add(new ConverterList());
        CONVERTERS.add(new ConverterMap());
        CONVERTERS.add(new ConverterSet());
        CONVERTERS.add(new ConverterDefault());
        CONVERTERS.add(new ConverterNumber());
        CONVERTERS.add(new ConverterString()); // catch-all
    }

}