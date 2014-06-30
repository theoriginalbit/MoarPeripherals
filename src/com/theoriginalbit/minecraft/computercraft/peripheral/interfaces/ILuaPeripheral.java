package com.theoriginalbit.minecraft.computercraft.peripheral.interfaces;

import dan200.computercraft.api.peripheral.IComputerAccess;

public interface ILuaPeripheral {
	public void attach(IComputerAccess computer);
	public void detach(IComputerAccess computer);
	public String getType();
}