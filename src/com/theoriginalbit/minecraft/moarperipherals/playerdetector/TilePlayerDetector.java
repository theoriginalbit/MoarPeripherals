package com.theoriginalbit.minecraft.moarperipherals.playerdetector;

import com.theoriginalbit.minecraft.moarperipherals.api.aware.IActivateAwareTile;
import com.theoriginalbit.minecraft.moarperipherals.generic.TilePeripheral;

import dan200.computercraft.api.lua.ILuaContext;
import dan200.computercraft.api.peripheral.IComputerAccess;
import net.minecraft.entity.player.EntityPlayer;

public class TilePlayerDetector extends TilePeripheral implements IActivateAwareTile {
	private static final String TYPE = "player_detector";
	private static final String EVENT_PLAYER = "player";

	public TilePlayerDetector() {
		super(TYPE);
	}
	
	@Override
	public boolean onActivated(EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if (player.isSneaking()) {
			return false;
		}
		computerQueueEvent(EVENT_PLAYER, player.username);
		return true;
	}

	@Override
	public Object[] callMethod(IComputerAccess computer, ILuaContext context, int method, Object[] arguments) throws Exception {
		return null;
	}

}
