package com.theoriginalbit.minecraft.computercraft.peripheral.wrapper;

import java.lang.reflect.Method;
import java.util.List;

import com.google.common.collect.Lists;
import com.theoriginalbit.minecraft.computercraft.peripheral.TilePeripheral;
import com.theoriginalbit.minecraft.computercraft.peripheral.annotation.LuaFunction;

import dan200.computercraft.api.lua.ILuaContext;
import dan200.computercraft.api.peripheral.IComputerAccess;
import dan200.computercraft.api.peripheral.IPeripheral;

public class PeripheralWrapper implements IPeripheral {
	private final TilePeripheral instance;
	private final List<MethodWrapper> methods = Lists.newArrayList();
	private final String[] methodNames;
	
	public PeripheralWrapper(TilePeripheral peripheral) {
		instance = peripheral;
		
		List<String> names = Lists.newArrayList();
		for (Method m : peripheral.getClass().getMethods()) {
			if (m.isAnnotationPresent(LuaFunction.class)) {
				MethodWrapper wrapper = new MethodWrapper(peripheral, m, m.getAnnotation(LuaFunction.class));
				methods.add(wrapper);
				names.add(wrapper.getLuaName());
			}
		}
		methodNames = names.toArray(new String[names.size()]);
	}

	@Override
	public String getType() {
		return instance.getType();
	}

	@Override
	public String[] getMethodNames() {
		return methodNames;
	}

	@Override
	public Object[] callMethod(IComputerAccess computer, ILuaContext context, int method, Object[] arguments) throws Exception {
		return methods.get(method).invoke(computer, context, arguments);
	}

	@Override
	public void attach(IComputerAccess computer) {
		instance.attach(computer);
	}

	@Override
	public void detach(IComputerAccess computer) {
		instance.detach(computer);
	}

	@Override
	public boolean equals(IPeripheral other) {
		return false;
	}

}
