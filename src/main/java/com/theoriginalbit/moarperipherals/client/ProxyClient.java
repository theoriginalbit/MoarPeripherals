/**
 * Copyright (c) 2013-2014, Joshua Asbury (@theoriginalbit)
 * http://wiki.theoriginalbit.com/moarperipherals/
 *
 * MoarPeripherals is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://www.mod-buildcraft.com/MMPL-1.0.txt
 */
package com.theoriginalbit.moarperipherals.client;

import com.theoriginalbit.moarperipherals.common.ProxyCommon;
import com.theoriginalbit.moarperipherals.common.config.ConfigHandler;
import com.theoriginalbit.moarperipherals.common.registry.ModBlocks;
import com.theoriginalbit.moarperipherals.common.registry.ModItems;
import com.theoriginalbit.moarperipherals.common.reference.Constants;
import com.theoriginalbit.moarperipherals.client.render.*;
import com.theoriginalbit.moarperipherals.common.tile.TileAntennaController;
import com.theoriginalbit.moarperipherals.common.tile.TileKeyboard;
import com.theoriginalbit.moarperipherals.common.tile.TilePrinter;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
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
            MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ModBlocks.blockKeyboard), rendererKeyboard);
            ClientRegistry.bindTileEntitySpecialRenderer(TileKeyboard.class, rendererKeyboard);
        }

        // Register optional renders when enabled
        if (ConfigHandler.enablePrinter && ConfigHandler.enablePrinterGfx) {
            // printer
            RendererPrinter rendererPrinter = new RendererPrinter();
            MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ModBlocks.blockPrinter), rendererPrinter);
            ClientRegistry.bindTileEntitySpecialRenderer(TilePrinter.class, rendererPrinter);
            // ink cartridge
//            MinecraftForgeClient.registerItemRenderer(ModItems.itemInkCartridge, new RendererItemInkCartridge());
        }

        if (ConfigHandler.isSonicEnabled() && ConfigHandler.enableSonicGfx) {
            MinecraftForgeClient.registerItemRenderer(ModItems.itemSonic, new RendererItemSonic());
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