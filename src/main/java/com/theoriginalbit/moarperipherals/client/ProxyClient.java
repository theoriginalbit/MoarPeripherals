/**
 * Copyright 2014 Joshua Asbury (@theoriginalbit)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
import com.theoriginalbit.moarperipherals.common.tile.TileMiniAntenna;
import com.theoriginalbit.moarperipherals.common.tile.TilePrinter;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;

@SuppressWarnings("unused")
public class ProxyClient extends ProxyCommon {

    private final Minecraft mc;

    @Override
    public void postInit() {
        MinecraftForge.EVENT_BUS.register(Icons.instance);
    }

    public ProxyClient() {
        mc = Minecraft.getMinecraft();
    }

    @Override
    public World getClientWorld(int dimId) {
        if (mc.theWorld.provider.dimensionId == dimId) {
            return mc.theWorld;
        }
        return null;
    }

    /*
     * Method sourced from WorldClient
     */
    @Override
    public void playSound(double x, double y, double z, String name, float volume, float pitch, boolean delayed) {
        double distanceSq = mc.renderViewEntity.getDistanceSq(x, y, z);
        final PositionedSoundRecord sound = new PositionedSoundRecord(new ResourceLocation(name), volume, pitch, (float) x, (float) y, (float) z);

        if (delayed && distanceSq > 100.0d) {
            double distance = Math.sqrt(distanceSq) / 40.0d;
            mc.getSoundHandler().playDelayedSound(sound, (int) (distance * 20.0d));
        } else {
            mc.getSoundHandler().playSound(sound);
        }
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
            MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ModBlocks.blockKeyboardMac), rendererKeyboard);
            MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ModBlocks.blockKeyboardPc), rendererKeyboard);
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

        if (ConfigHandler.enableMiniAntenna) {
            RendererMiniAntenna rendererMiniAntenna = new RendererMiniAntenna();
            ClientRegistry.bindTileEntitySpecialRenderer(TileMiniAntenna.class, rendererMiniAntenna);
        }
    }

    @Override
    public boolean isOp(EntityPlayer player) {
        return false;
    }

}