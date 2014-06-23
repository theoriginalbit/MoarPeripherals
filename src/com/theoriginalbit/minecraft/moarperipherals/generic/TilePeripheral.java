package com.theoriginalbit.minecraft.moarperipherals.generic;

import java.util.ArrayList;

import org.apache.commons.lang3.ArrayUtils;

import com.google.common.collect.Lists;

import dan200.computercraft.api.peripheral.IComputerAccess;
import dan200.computercraft.api.peripheral.IPeripheral;
import net.minecraft.tileentity.TileEntity;

public abstract class TilePeripheral extends TileEntity implements IPeripheral {
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

	@Override
	public boolean equals(IPeripheral other) {
		return false;
	}

	protected void computerQueueEvent(String event, Object... args) {
		for (IComputerAccess computer : computers) {
			computer.queueEvent(event, ArrayUtils.add(args, 0, computer.getAttachmentName()));
		}
	}
}
