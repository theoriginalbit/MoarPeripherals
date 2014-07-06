package com.theoriginalbit.minecraft.moarperipherals.proxy;

import com.theoriginalbit.minecraft.moarperipherals.interfaces.IProxy;

import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;

public class ProxyServer implements IProxy {

	@Override
	public World getClientWorld(int dimId) {
		return MinecraftServer.getServer().worldServerForDimension(dimId);
	}

	@Override
	public boolean isServer() {
		return true;
	}

	@Override
	public void registerRenderInfo() {}
}