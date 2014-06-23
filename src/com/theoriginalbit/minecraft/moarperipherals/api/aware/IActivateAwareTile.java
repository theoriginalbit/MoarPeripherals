package com.theoriginalbit.minecraft.moarperipherals.api.aware;

import net.minecraft.entity.player.EntityPlayer;

public interface IActivateAwareTile {
	public boolean onActivated(EntityPlayer player, int side, float hitX, float hitY, float hitZ);
}
