package com.theoriginalbit.minecraft.computercraft.peripheral.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.theoriginalbit.minecraft.computercraft.peripheral.LuaType;

/**
 * Allows you to specify the Lua type of a method, for argument type enforcement
 * and conversion
 * 
 * @author theoriginalbit
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface Arg {
	public LuaType value();	
}