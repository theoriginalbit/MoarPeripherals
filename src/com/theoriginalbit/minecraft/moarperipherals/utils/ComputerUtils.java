package com.theoriginalbit.minecraft.moarperipherals.utils;

import java.lang.reflect.Method;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public final class ComputerUtils {
	
	private static final Class<?> CLAZZ_TILECOMPUTERBASE = ReflectionUtils.getClass("dan200.computercraft.shared.computer.blocks.TileComputerBase");
	private static final Method METHOD_GETCOMPUTER = ReflectionUtils.getMethod(CLAZZ_TILECOMPUTERBASE, "getComputer", new Class[0]);
	
	private static final Class<?> CLAZZ_ICOMPUTER = ReflectionUtils.getClass("dan200.computercraft.shared.computer.core.IComputer");
	private static final Method METHOD_QUEUEEVENT = ReflectionUtils.getMethod(CLAZZ_ICOMPUTER, "queueEvent", new Class[]{ String.class, Object[].class });
	private static final Method METHOD_ISON = ReflectionUtils.getMethod(CLAZZ_ICOMPUTER, "isOn", new Class[]{});
	private static final Method METHOD_TURNON = ReflectionUtils.getMethod(CLAZZ_ICOMPUTER, "turnOn", new Class[]{});
	private static final Method METHOD_SHUTDOWN = ReflectionUtils.getMethod(CLAZZ_ICOMPUTER, "shutdown", new Class[]{});
	private static final Method METHOD_REBOOT = ReflectionUtils.getMethod(CLAZZ_ICOMPUTER, "reboot", new Class[]{});
	
	public static TileEntity getTileComputerBase(World world, Integer x, Integer y, Integer z) {
		if (x == null || y == null || z == null) { return null; }
		TileEntity tile = world.getBlockTileEntity(x, y, z);
		if (tile != null && CLAZZ_TILECOMPUTERBASE.isAssignableFrom(tile.getClass())) {
			return tile;
		}
		return null;
	}
	
	public static Object getIComputer(World world, int x, int y, int z) {
		TileEntity tile = getTileComputerBase(world, x, y, z);
		if (tile != null) {
			return ReflectionUtils.callMethod(tile, METHOD_GETCOMPUTER);
		}
		return null;
	}
	
	public static void queueEvent(TileEntity tile, String event, Object...args) {
		Object computer = getIComputer(tile.worldObj, tile.xCoord, tile.yCoord, tile.zCoord);
		
		if (computer != null && CLAZZ_ICOMPUTER.isAssignableFrom(computer.getClass())) {
			ReflectionUtils.callMethod(computer, METHOD_QUEUEEVENT, event, args);
		}
	}
	
	public static boolean isOn(TileEntity tile) {
		Object computer = getIComputer(tile.worldObj, tile.xCoord, tile.yCoord, tile.zCoord);
		
		if (computer != null && CLAZZ_ICOMPUTER.isAssignableFrom(computer.getClass())) {
			return (Boolean) ReflectionUtils.callMethod(computer, METHOD_ISON);
		}
		
		return false;
	}
	
	public static void turnOn(TileEntity tile) {
		Object computer = getIComputer(tile.worldObj, tile.xCoord, tile.yCoord, tile.zCoord);
		
		if (computer != null && CLAZZ_ICOMPUTER.isAssignableFrom(computer.getClass())) {
			ReflectionUtils.callMethod(computer, METHOD_TURNON);
		}
	}
	
	public static void shutdown(TileEntity tile) {
		Object computer = getIComputer(tile.worldObj, tile.xCoord, tile.yCoord, tile.zCoord);
		
		if (computer != null && CLAZZ_ICOMPUTER.isAssignableFrom(computer.getClass())) {
			ReflectionUtils.callMethod(computer, METHOD_SHUTDOWN);
		}
	}
	
	public static void reboot(TileEntity tile) {
		Object computer = getIComputer(tile.worldObj, tile.xCoord, tile.yCoord, tile.zCoord);
		
		if (computer != null && CLAZZ_ICOMPUTER.isAssignableFrom(computer.getClass())) {
			ReflectionUtils.callMethod(computer, METHOD_REBOOT);
		}
	}
	
	public static void terminate(TileEntity tile) {
		queueEvent(tile, "terminate");
	}
	
	public static void paste(TileEntity tile, String clipboard) {
		queueEvent(tile, "paste", clipboard);
	}
}