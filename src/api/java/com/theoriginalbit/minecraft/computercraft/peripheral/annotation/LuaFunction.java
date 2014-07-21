package com.theoriginalbit.minecraft.computercraft.peripheral.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

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
 * Marks a Java method in your peripheral as a Lua accessible method.
 * @author theoriginalbit
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LuaFunction {

    /**
     * This value will determine what the corresponding Lua function will be called. 
     * By default, the Lua function will go by the same name as the method it is created from.
     */
    public String name() default "";

    /**
     * If your method returns an Object[] and you don't want it to be handled by
     * the framework's automatic conversion utility, set this flag to true.
     */
    public boolean isMultiReturn() default false;

    /**
     * Use this to only enable this method when certain mods are found installed in
     * this Minecraft instance, these values should be the Mod's ID
     */
    public String[] modIds() default {};

}