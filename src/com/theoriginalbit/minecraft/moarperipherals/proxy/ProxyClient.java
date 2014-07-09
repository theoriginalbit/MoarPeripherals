package com.theoriginalbit.minecraft.moarperipherals.proxy;

import com.theoriginalbit.minecraft.moarperipherals.interfaces.IProxy;
import com.theoriginalbit.minecraft.moarperipherals.reference.Settings;
import com.theoriginalbit.minecraft.moarperipherals.render.RendererItemInkCartridge;
import com.theoriginalbit.minecraft.moarperipherals.render.RendererKeyboard;
import com.theoriginalbit.minecraft.moarperipherals.tile.TileKeyboard;

import cpw.mods.fml.client.registry.ClientRegistry;
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
		// Register Ink Cartridge renderer
		if (Settings.enablePrinter && Settings.enableInkCartridgeRenderer) {
			MinecraftForgeClient.registerItemRenderer(Settings.itemIdInkCartridge, new RendererItemInkCartridge());
		}
		
		// Register Keyboard renderers
		if (Settings.enableKeyboard) {
			RendererKeyboard rendererKeyboard = new RendererKeyboard();
			MinecraftForgeClient.registerItemRenderer(Settings.blockIdKeyboard, rendererKeyboard);
			ClientRegistry.bindTileEntitySpecialRenderer(TileKeyboard.class, rendererKeyboard);
		}
	}
}