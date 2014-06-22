package com.theoriginalbit.minecraft.moarperipherals.playerdetector;

import com.theoriginalbit.minecraft.moarperipherals.TilePeripheral;

import dan200.computercraft.api.lua.ILuaContext;
import dan200.computercraft.api.peripheral.IComputerAccess;
import net.minecraft.entity.player.EntityPlayer;

public class TilePlayerDetector extends TilePeripheral {
	private static final String TYPE = "player_detector";
	private static final String EVENT_PLAYER = "player";
	private static final String[] METHOD_NAMES = new String[0];

	public TilePlayerDetector() {
		super(TYPE);
	}

	public boolean onActivated(EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		computerQueueEvent(EVENT_PLAYER, player.username);
		return true;
	}

	@Override
	public String[] getMethodNames() {
		return METHOD_NAMES;
	}

	@Override
	public Object[] callMethod(IComputerAccess computer, ILuaContext context, int method, Object[] arguments) throws Exception {
		return null;
	}

}
