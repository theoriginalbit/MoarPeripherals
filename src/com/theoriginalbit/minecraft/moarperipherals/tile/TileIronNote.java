package com.theoriginalbit.minecraft.moarperipherals.tile;

import dan200.computercraft.api.lua.ILuaContext;
import dan200.computercraft.api.peripheral.IComputerAccess;

public class TileIronNote extends TilePeripheral {
	private static final String TYPE = "iron_note";
	private static final String[] METHODS = new String[]{"validInstrument", "playNote"};

	public TileIronNote() {
		super(TYPE, METHODS);
	}

	@Override
	public Object[] callMethod(IComputerAccess computer, ILuaContext context, int method, Object[] arguments) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
