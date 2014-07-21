package com.theoriginalbit.minecraft.computercraft.peripheral.wrapper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.google.common.base.Preconditions;
import com.theoriginalbit.minecraft.computercraft.peripheral.LuaType;
import com.theoriginalbit.minecraft.computercraft.peripheral.annotation.LuaFunction;

import dan200.computercraft.api.lua.ILuaContext;
import dan200.computercraft.api.peripheral.IComputerAccess;

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
 * This wraps the method supplied from the LuaPeripheral, it provides
 * a simple invocation which converts the Lua arguments supplied to
 * the Java type expected of the LuaFunction, as well as converting
 * the return value back to Lua types. It will also provide user friendly
 * errors when the method is invoked incorrectly, such as 'expected string
 * got number' or 'expected 4 arguments, got 3'
 *
 * IMPORTANT:
 * This is a backend class, you should never need to use this, and
 * modifying this may have unexpected results.
 *
 * @author theoriginalbit
 */
public class MethodWrapper {

	private final Method method;
	private final Object instance;
    private final int luaParamsCount;
    private final Class<?>[] javaParams;
    private final boolean isMultiReturn;
	
	public MethodWrapper(Object peripheral, Method m) {
        // why? just 'cause
		Preconditions.checkArgument(m.isAnnotationPresent(LuaFunction.class));

		instance = peripheral;
		method = m;
		javaParams = method.getParameterTypes();
        isMultiReturn = m.getAnnotation(LuaFunction.class).isMultiReturn();

        // count how many parameters are required from Lua
        int count = javaParams.length;
        for (Class<?> clazz : javaParams) {
            if (IComputerAccess.class.isAssignableFrom(clazz)) {
                --count;
            } else if (ILuaContext.class.isAssignableFrom(clazz)) {
                --count;
            }
        }

        luaParamsCount = count;
	}

	public Object[] invoke(IComputerAccess access, ILuaContext context, Object[] arguments) throws Exception {
        // make sure they've provided enough args
        Preconditions.checkArgument(arguments.length == luaParamsCount, String.format("expected %d arg(s), got %d", luaParamsCount, arguments.length));

		Object[] args = new Object[javaParams.length];
		for (int i = 0; i < args.length; ++i) {
            if (IComputerAccess.class.isAssignableFrom(javaParams[i])) {
				args[i] = access;
			} else if (ILuaContext.class.isAssignableFrom(javaParams[i])) {
				args[i] = context;
			} else if (arguments[i] != null) {
				final Object convert = LuaType.fromLua(arguments[i], javaParams[i]);
				Preconditions.checkArgument(convert != null, "expected %s, got %s", LuaType.getLuaName(javaParams[i]), LuaType.getLuaName(arguments[i].getClass()));
				args[i] = convert;
			} else {
                throw new Exception(String.format("expected %s, got nil", LuaType.getLuaName(javaParams[i])));
            }
		}
		
		try {
            if (isMultiReturn) {
                // get the result
                final Object[] result = (Object[]) method.invoke(instance, args);
                // convert its inner members
                for (int i = 0; i < result.length; ++i) {
                    result[i] = LuaType.toLua(result[i]);
                }
                return result;
            } else {
                // return the result converted, if the method returns Object[] this will be converted to a Map
                return new Object[] { LuaType.toLua(method.invoke(instance, args)) };
            }
		} catch (IllegalAccessException e) {
            e.printStackTrace();
			throw new Exception("Developer problem, please present your client log file to the developer of this peripheral.");
		} catch (IllegalArgumentException e) {
            e.printStackTrace();
			throw new Exception("Developer problem, please present your client log file to the developer of this peripheral.");
		} catch (InvocationTargetException e) {
			String message;
			Throwable cause = e;
			while ((message = cause.getMessage()) == null
					&& (cause = cause.getCause()) != null) {}
			throw new Exception(message);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

}