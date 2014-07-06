package com.theoriginalbit.minecraft.computercraft.peripheral.wrapper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.google.common.base.Preconditions;
import com.theoriginalbit.minecraft.computercraft.peripheral.LuaType;
import com.theoriginalbit.minecraft.computercraft.peripheral.TilePeripheral;
import com.theoriginalbit.minecraft.computercraft.peripheral.annotation.Alias;
import com.theoriginalbit.minecraft.computercraft.peripheral.annotation.LuaFunction;

import dan200.computercraft.api.lua.ILuaContext;
import dan200.computercraft.api.peripheral.IComputerAccess;

public class MethodWrapper {
	private final String name;
	private final Method method;
	private final Class<?>[] javaParams;
	private final TilePeripheral instance;
	
	public MethodWrapper(TilePeripheral peripheral, Method method, LuaFunction function) {
		Preconditions.checkArgument(method.isAnnotationPresent(LuaFunction.class));
		
		instance = peripheral;
		this.method = method;
		// get the name either from the @Alias or the Java name
		name = method.isAnnotationPresent(Alias.class) ? method.getAnnotation(Alias.class).value() : method.getName();
		
		javaParams = method.getParameterTypes();
	}
	
	public String getLuaName() {
		return name;
	}
	
	public Object[] invoke(IComputerAccess access, ILuaContext context, Object[] arguments) throws Exception {
		Object[] args = new Object[javaParams.length];
		
		for (int i = 0; i < args.length; ++i) {
			if (arguments[i] == null) {
				throw new Exception(String.format("expected %s, got nil", LuaType.findName(javaParams[i])));
			} else if (IComputerAccess.class.isAssignableFrom(javaParams[i])) {
				args[i] = access;
			} else if (ILuaContext.class.isAssignableFrom(javaParams[i])) {
				args[i] = context;
			} else {
				Object convert = LuaType.fromLua(arguments[i], javaParams[i]);
				Preconditions.checkArgument(convert != null, "expected %s, got %s", LuaType.findName(javaParams[i]), LuaType.findName(arguments[i].getClass()));
				args[i] = convert;
			}
		}
		
		try {
			return new Object[] { LuaType.toLua(method.invoke(instance, args)) };
		} catch (IllegalAccessException e) {
			throw new Exception("Developer problem, IllegalAccessException " + e.getMessage());
		} catch (IllegalArgumentException e) {
			throw new Exception("Developer problem, IllegalArgumentException " + e.getMessage());
		} catch (InvocationTargetException e) {
			String message = null;
			Throwable cause = e;
			while ((message = cause.getMessage()) == null
					&& (cause = cause.getCause()) != null) {}
			throw new Exception(message);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
}