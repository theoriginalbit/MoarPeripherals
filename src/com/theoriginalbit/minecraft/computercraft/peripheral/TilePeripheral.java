package com.theoriginalbit.minecraft.computercraft.peripheral;

import java.util.ArrayList;

import openperipheral.api.Ignore;

import org.apache.commons.lang3.ArrayUtils;

import com.google.common.collect.Lists;
import com.theoriginalbit.minecraft.computercraft.peripheral.interfaces.ILuaPeripheral;

import dan200.computercraft.api.peripheral.IComputerAccess;
import net.minecraft.tileentity.TileEntity;

@Ignore
public abstract class TilePeripheral extends TileEntity implements ILuaPeripheral {
	protected ArrayList<IComputerAccess> computers = Lists.newArrayList();
	protected String type;
	
	public TilePeripheral(String peripheralType) {
		type = peripheralType;
	}

	@Override
	public String getType() {
		return type;
	}

	@Override
	public void attach(IComputerAccess computer) {
		computers.add(computer);
	}

	@Override
	public void detach(IComputerAccess computer) {
		computers.remove(computer);
	}

	protected void computerQueueEvent(String event, Object... args) {
		for (IComputerAccess computer : computers) {
			computer.queueEvent(event, ArrayUtils.add(args, 0, computer.getAttachmentName()));
		}
	}
}