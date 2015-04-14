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
package com.theoriginalbit.framework.peripheral.annotation.function;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks a Java method in your {@link com.theoriginalbit.framework.peripheral.annotation.LuaPeripheral} as a
 * Lua accessible method. By default the function name will be the Java method name, however you can provide an
 * alternative name for it through the value in this annotation. The difference between providing a value through this
 * annotation over the {@link com.theoriginalbit.framework.peripheral.annotation.function.Alias} annotation
 * is it Java method name will not be usable, whereas with @Alias it still is usable.
 *
 * @author theoriginalbit
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LuaFunction {
    /**
     * This value will determine what the corresponding Lua function will be called.
     * By default, the Lua function will go by the same name as the method it is created from.
     */
    public String value() default "";
}