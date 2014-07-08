package com.theoriginalbit.minecraft.moarperipherals.tile;

import com.theoriginalbit.minecraft.moarperipherals.interfaces.aware.IActivateAwareTile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;

public class TileKeyboard extends TileEntity implements IActivateAwareTile {
	private boolean connected = false;
	
	public boolean hasConnection() {
		return connected;
	}

	@Override
	public boolean onActivated(EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		connected = !connected;
		return true;
	}
}