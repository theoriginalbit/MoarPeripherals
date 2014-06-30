package com.theoriginalbit.minecraft.computercraft.peripheral;

import java.util.Deque;
import java.util.Map;

import net.minecraftforge.common.ForgeDirection;

import com.google.common.collect.Lists;
import com.theoriginalbit.minecraft.computercraft.peripheral.converter.ConverterArray;
import com.theoriginalbit.minecraft.computercraft.peripheral.converter.ConverterDefault;
import com.theoriginalbit.minecraft.computercraft.peripheral.converter.ConverterForgeDirection;
import com.theoriginalbit.minecraft.computercraft.peripheral.converter.ConverterList;
import com.theoriginalbit.minecraft.computercraft.peripheral.converter.ConverterMap;
import com.theoriginalbit.minecraft.computercraft.peripheral.converter.ConverterNumber;
import com.theoriginalbit.minecraft.computercraft.peripheral.converter.ConverterSet;
import com.theoriginalbit.minecraft.computercraft.peripheral.converter.ConverterString;
import com.theoriginalbit.minecraft.computercraft.peripheral.interfaces.ITypeConverter;

import dan200.computercraft.api.lua.ILuaObject;

/**
 * An Enum to specify the mapping between Java classes and Lua types
 * 
 * @author theoriginalbit
 */
public enum LuaType {
	TABLE("table", Map.class),
	NUMBER("number", Double.class),
	STRING("string", String.class),
	NIL("nil", void.class),
	BOOLEAN("boolean", Boolean.class),
	FORGEDIRECTION("direction", ForgeDirection.class),
	OBJECT("?", Object.class);
	
	private static final Deque<ITypeConverter> CONVERTERS = Lists.newLinkedList();
	
	private final String luaName;
	private final Class<?> javaType;
	
	private LuaType(String name, Class<?> type) {
		luaName = name;
		javaType = type;
	}
	
	public Class<?> getJavaType() {
		return javaType;
	}
	
	public String getLuaName() {
		return luaName;
	}
	
	public static String findName(Class<?> clazz) {
		if (clazz == null) {
			return "nil";
		}
		for (LuaType t : values()) {
			if (clazz.isAssignableFrom(t.getJavaType())) {
				return t.getLuaName();
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
		CONVERTERS.add(new ConverterForgeDirection());
		// Order is important from here on!
		CONVERTERS.add(new ConverterArray());
		CONVERTERS.add(new ConverterList());
		CONVERTERS.add(new ConverterMap());
		CONVERTERS.add(new ConverterSet());
		CONVERTERS.add(new ConverterDefault());
		CONVERTERS.add(new ConverterNumber());
		CONVERTERS.add(new ConverterString()); // catch-all
	}
}