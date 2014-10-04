/**
 * Copyright (c) 2013-2014, Joshua Asbury (@theoriginalbit)
 * http://wiki.theoriginalbit.com/moarperipherals/
 *
 * MoarPeripherals is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://www.mod-buildcraft.com/MMPL-1.0.txt
 */
package com.theoriginalbit.minecraft.moarperipherals.client;

import com.theoriginalbit.minecraft.moarperipherals.common.ProxyCommon;
import com.theoriginalbit.minecraft.moarperipherals.common.config.ConfigHandler;
import com.theoriginalbit.minecraft.moarperipherals.common.reference.Constants;
import com.theoriginalbit.minecraft.moarperipherals.client.render.*;
import com.theoriginalbit.minecraft.moarperipherals.common.tile.TileAntennaController;
import com.theoriginalbit.minecraft.moarperipherals.common.tile.TileKeyboard;
import com.theoriginalbit.minecraft.moarperipherals.common.tile.TilePrinter;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;

public class ProxyClient extends ProxyCommon {

    private final Minecraft mc;

    public ProxyClient() {
        mc = Minecraft.getMinecraft();
    }

    @Override
    public World getClientWorld(int dimId) {
        return mc.theWorld;
    }

    @Override
    public boolean isClient() {
        return FMLCommonHandler.instance().getEffectiveSide().isClient();
    }

    @Override
    public void registerRenderInfo() {
        // Register Keyboard renderers
        if (ConfigHandler.enableKeyboard) {
            RendererKeyboard rendererKeyboard = new RendererKeyboard();
            MinecraftForgeClient.registerItemRenderer(ConfigHandler.blockIdKeyboard, rendererKeyboard);
            ClientRegistry.bindTileEntitySpecialRenderer(TileKeyboard.class, rendererKeyboard);
        }

        // Register optional renders when enabled
        if (ConfigHandler.enablePrinter && ConfigHandler.enablePrinterGfx) {
            // printer
            RendererPrinter rendererPrinter = new RendererPrinter();
            MinecraftForgeClient.registerItemRenderer(ConfigHandler.blockIdPrinter, rendererPrinter);
            ClientRegistry.bindTileEntitySpecialRenderer(TilePrinter.class, rendererPrinter);
            // ink cartridge
            MinecraftForgeClient.registerItemRenderer(ConfigHandler.itemIdInkCartridge, new RendererItemInkCartridge());
        }

        if (ConfigHandler.isSonicEnabled() && ConfigHandler.enableSonicGfx) {
            MinecraftForgeClient.registerItemRenderer(ConfigHandler.itemIdSonic, new RendererItemSonic());
        }

        if (ConfigHandler.enableAntenna) {
            Constants.RENDER_ID.ANTENNA = RenderingRegistry.getNextAvailableRenderId();
            Constants.RENDER_ID.ANTENNA_CTRLR = RenderingRegistry.getNextAvailableRenderId();
            RenderingRegistry.registerBlockHandler(Constants.RENDER_ID.ANTENNA, new RendererAntenna());
            RenderingRegistry.registerBlockHandler(Constants.RENDER_ID.ANTENNA_CTRLR, new RendererAntennaController());
            ClientRegistry.bindTileEntitySpecialRenderer(TileAntennaController.class, new RendererTileAntenna());
        }

    }

    @Override
    public boolean isOp(EntityPlayer player) {
        return false;
    }

}