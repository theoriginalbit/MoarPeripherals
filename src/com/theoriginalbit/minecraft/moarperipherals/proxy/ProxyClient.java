package com.theoriginalbit.minecraft.moarperipherals.proxy;

import com.theoriginalbit.minecraft.moarperipherals.interfaces.IProxy;
import com.theoriginalbit.minecraft.moarperipherals.reference.Settings;
import com.theoriginalbit.minecraft.moarperipherals.render.ItemRenderInkCartridge;

import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;

public class ProxyClient implements IProxy {
	private final Minecraft mc;
	
	public ProxyClient() {
		mc = Minecraft.getMinecraft();
	}

	@Override
	public World getClientWorld(int dimId) {
		return mc.theWorld;
	}

	@Override
	public boolean isServer() {
		return FMLCommonHandler.instance().getEffectiveSide().isServer();
	}

	@Override
	public void registerRenderInfo() {
		MinecraftForgeClient.registerItemRenderer(Settings.itemIdInkCartridge, new ItemRenderInkCartridge());
	}
}