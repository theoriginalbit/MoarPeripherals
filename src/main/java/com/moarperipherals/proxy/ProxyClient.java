/**
 * Copyright 2014-2015 Joshua Asbury (@theoriginalbit)
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.moarperipherals.proxy;

import com.moarperipherals.Constants;
import com.moarperipherals.client.render.*;
import com.moarperipherals.config.ConfigData;
import com.moarperipherals.init.ModBlocks;
import com.moarperipherals.init.ModItems;
import com.moarperipherals.integration.registry.UpgradeRegistry;
import com.moarperipherals.tile.TileAntennaController;
import com.moarperipherals.tile.TileKeyboard;
import com.moarperipherals.tile.TileMiniAntenna;
import com.moarperipherals.tile.TilePrinter;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import dan200.computercraft.api.turtle.ITurtleUpgrade;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.common.MinecraftForge;

import java.io.File;

@SuppressWarnings("unused")
public class ProxyClient extends ProxyCommon {

    private final Minecraft mc;

    public ProxyClient() {
        mc = Minecraft.getMinecraft();
    }

    @Override
    public void postInit() {
        MinecraftForge.EVENT_BUS.register(this);
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
        if (ConfigData.enableKeyboard) {
            RendererKeyboard rendererKeyboard = new RendererKeyboard();
            MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ModBlocks.blockKeyboardMac), rendererKeyboard);
            MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ModBlocks.blockKeyboardPc), rendererKeyboard);
            ClientRegistry.bindTileEntitySpecialRenderer(TileKeyboard.class, rendererKeyboard);
        }

        // Register optional renders when enabled
        if (ConfigData.enablePrinter && ConfigData.enablePrinterGfx) {
            // printer
            RendererPrinter rendererPrinter = new RendererPrinter();
            MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ModBlocks.blockPrinter), rendererPrinter);
            ClientRegistry.bindTileEntitySpecialRenderer(TilePrinter.class, rendererPrinter);
        }

        // Register ink cartridge renderer
        if (ConfigData.enablePrinter && ConfigData.enableInkCartridgeGfx) {
            MinecraftForgeClient.registerItemRenderer(ModItems.itemInkCartridge, new RendererInkCartridge());
        }

        if (ConfigData.isSonicEnabled() && ConfigData.enableSonicGfx) {
            MinecraftForgeClient.registerItemRenderer(ModItems.itemSonic, new RendererItemSonic());
        }

        if (ConfigData.enableAntenna) {
            Constants.RENDER_ID.ANTENNA = RenderingRegistry.getNextAvailableRenderId();
            Constants.RENDER_ID.ANTENNA_CTRLR = RenderingRegistry.getNextAvailableRenderId();
            RenderingRegistry.registerBlockHandler(Constants.RENDER_ID.ANTENNA, new RendererAntenna());
            RenderingRegistry.registerBlockHandler(Constants.RENDER_ID.ANTENNA_CTRLR, new RendererAntennaController());
            ClientRegistry.bindTileEntitySpecialRenderer(TileAntennaController.class, new RendererTileAntenna());
        }

        if (ConfigData.enableMiniAntenna) {
            RendererMiniAntenna rendererMiniAntenna = new RendererMiniAntenna();
            ClientRegistry.bindTileEntitySpecialRenderer(TileMiniAntenna.class, rendererMiniAntenna);
        }
    }

    @Override
    public boolean isOp(EntityPlayer player) {
        return false;
    }

    @Override
    public File getBase() {
        return Minecraft.getMinecraft().mcDataDir;
    }

    @SubscribeEvent
    public void onPreTextureStitch(TextureStitchEvent.Pre event) {
        addTextures(event.map);
    }

    private void addTextures(TextureMap map) {
        boolean terrain = map.getTextureType() == 1;
        if (terrain) {
            for (ITurtleUpgrade upgrade : UpgradeRegistry.UPGRADES) {
                if (upgrade instanceof IUpgradeIcon) {
                    ((IUpgradeIcon) upgrade).registerIcons(map);
                }
            }
        } else {
            for (ITurtleUpgrade upgrade : UpgradeRegistry.UPGRADES) {
                if (upgrade instanceof IUpgradeToolIcon) {
                    ((IUpgradeToolIcon) upgrade).registerIcons(map);
                }
            }
        }
    }

}