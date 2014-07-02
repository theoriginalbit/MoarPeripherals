package com.theoriginalbit.minecraft.computercraft.peripheral;

import java.util.ArrayList;

import openperipheral.api.Ignore;

import org.apache.commons.lang3.ArrayUtils;

import com.google.common.collect.Lists;
import com.theoriginalbit.minecraft.computercraft.peripheral.annotation.LuaFunction;

import dan200.computercraft.api.peripheral.IComputerAccess;
import dan200.computercraft.api.peripheral.IPeripheral;
import net.minecraft.tileentity.TileEntity;

/**
 * The base {@link TileEntity} of peripherals, it implements what the
 * {@link PeripheralProvider} is looking for to wrap peripherals, it also
 * implements required methods so that your {@link TileEntity} does not need to
 * implement any of ComputerCraft's {@link IPeripheral} methods, you simply have
 * your {@link TileEntity} extend this and implement methods annotated with
 * {@link LuaFunction}
 * 
 * @author theoriginalbit
 */
@Ignore
public class TilePeripheral extends TileEntity {
	protected ArrayList<IComputerAccess> computers = Lists.newArrayList();
	protected String type;
	
	protected TilePeripheral(String peripheralType) {
		type = peripheralType;
	}
	
	public String getType() {
		return type;
	}
	
	public void attach(IComputerAccess computer) {
		computers.add(computer);
	}
	
	public void detach(IComputerAccess computer) {
		computers.remove(computer);
	}
	
	/**
	 * Queue an event on all attached computers, do note that the peripheral's
	 * attachment name is added as the first returned argument
	 * 
	 * @param event		the event name
	 * @param args 		the event arguments
	 */
	protected void computerQueueEvent(String event, Object... args) {
		for (IComputerAccess computer : computers) {
			computer.queueEvent(event, ArrayUtils.add(args, 0, computer.getAttachmentName()));
		}
	}
}