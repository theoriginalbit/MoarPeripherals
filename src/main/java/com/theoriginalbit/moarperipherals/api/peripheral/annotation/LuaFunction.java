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
package com.theoriginalbit.moarperipherals.api.peripheral.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

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