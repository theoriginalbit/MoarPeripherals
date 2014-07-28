package com.theoriginalbit.minecraft.framework.peripheral.converter;

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
 * Provides a common interface for type converters, instances of this
 * converter should be registered with the LuaType enum.
 *
 * You are required to define two methods, one for converting from a
 * Lua object to a Java object, and the other one to convert back
 * from a Java object to a Lua object. Be aware that Lua deals with
 * a very small and specific subset of data types!
 *
 * It is highly suggested when adding type converters to also add a
 * name mapping through LuaType#registerClassToNameMapping so that
 * if an error arises Lua-side your custom class will show a readable
 * name as opposed to '?'
 *
 * An example of a type converter and registering its name mapping can
 * be found in the example mod url:
 * https://github.com/theoriginalbit/Peripheral-Framework/tree/master/example
 *
 * @author theoriginalbit
 */
public interface ITypeConverter {

    /**
     * Convert the object from a Lua object to a Java object for use
     * in LuaFunctions. Return null if you don't provide conversion
     * for the class in the `expected` argument.
     *
     * @param obj the object from the Lua method call
     * @param expected the class type of the argument, i.e. String.class
     * @return your Java object or null if you don't convert
     */
	public Object fromLua(Object obj, Class<?> expected);

    /**
     * Convert the object from a Java object to a Lua object for
     * use after your method returns, Return null if you don't
     * provide conversion for the object.
     *
     * @param obj the object from the method call return
     * @return the Lua object or null if you don't convert
     */
	public Object toLua(Object obj);

}