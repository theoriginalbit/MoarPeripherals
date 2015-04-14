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

import dan200.computercraft.api.lua.LuaException;

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
	public Object fromLua(Object obj, Class<?> expected) throws LuaException;

    /**
     * Convert the object from a Java object to a Lua object for
     * use after your method returns, Return null if you don't
     * provide conversion for the object.
     *
     * @param obj the object from the method call return
     * @return the Lua object or null if you don't convert
     */
	public Object toLua(Object obj) throws LuaException;

}