package com.theoriginalbit.minecraft.moarperipherals.interfaces;

import net.minecraft.world.World;

public interface IProxy {	
	public World getClientWorld(int dimId);
	public boolean isServer();
}