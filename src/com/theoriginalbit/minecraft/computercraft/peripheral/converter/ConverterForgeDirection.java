package com.theoriginalbit.minecraft.computercraft.peripheral.converter;

import net.minecraftforge.common.ForgeDirection;

import com.theoriginalbit.minecraft.computercraft.peripheral.interfaces.ITypeConverter;

public class ConverterForgeDirection implements ITypeConverter {
	@Override
	public Object fromLua(Object obj, Class<?> expected) {
		if (expected == ForgeDirection.class && obj instanceof String) {
			ForgeDirection dir = ForgeDirection.valueOf((String) obj);
			return dir != null ? dir : ForgeDirection.UNKNOWN;
		}
		return null;
	}

	@Override
	public Object toLua(Object obj) {
		if (obj instanceof ForgeDirection) {
			return ((ForgeDirection) obj).name().toLowerCase();
		}
		return null;
	}

}
