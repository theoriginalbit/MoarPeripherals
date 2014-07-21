package com.theoriginalbit.minecraft.computercraft.peripheral;

import java.util.*;

import com.google.common.collect.Maps;

import com.google.common.collect.Lists;
import com.theoriginalbit.minecraft.computercraft.peripheral.converter.ConverterArray;
import com.theoriginalbit.minecraft.computercraft.peripheral.converter.ConverterDefault;
import com.theoriginalbit.minecraft.computercraft.peripheral.converter.ConverterList;
import com.theoriginalbit.minecraft.computercraft.peripheral.converter.ConverterMap;
import com.theoriginalbit.minecraft.computercraft.peripheral.converter.ConverterNumber;
import com.theoriginalbit.minecraft.computercraft.peripheral.converter.ConverterSet;
import com.theoriginalbit.minecraft.computercraft.peripheral.converter.ConverterString;
import com.theoriginalbit.minecraft.computercraft.peripheral.converter.ITypeConverter;

import dan200.computercraft.api.lua.ILuaObject;

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
 * An Enum to specify the mapping between Java classes and Lua types
 * and perform data type conversion between both languages.
 *
 * If you have another ITypeConverter to register with this system
 * you can do it in one of your mods initialisation phases. See
 * the registerTypeConverter method for more information.
 *
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
     *
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
            if (entry.getKey().isAssignableFrom(clazz)) {
                return entry.getValue();
            }
        }

        return "?";
    }

	public static Object fromLua(Object obj, Class<?> expected) {
		for (ITypeConverter converter : CONVERTERS) {
			Object response = converter.fromLua(obj, expected);
			if (response != null) { return response; }
		}
		return null;
	}
	
	public static Object toLua(Object obj) {
		if (obj == null || obj instanceof ILuaObject) { return obj; }
		
		for (ITypeConverter converter : CONVERTERS) {
			Object response = converter.toLua(obj);
			if (response != null) { return response; }
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