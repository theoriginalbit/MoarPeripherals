package com.theoriginalbit.minecraft.moarperipherals.interfaces.aware;

import net.minecraft.entity.player.EntityPlayer;

public interface IActivateAwareTile {
	public boolean onActivated(EntityPlayer player, int side, float hitX, float hitY, float hitZ);
}
