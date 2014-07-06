package com.theoriginalbit.minecraft.moarperipherals.proxy;

import net.minecraft.world.World;

public interface IProxy {	
	public World getClientWorld(int dimId);
	public boolean isServer();
}