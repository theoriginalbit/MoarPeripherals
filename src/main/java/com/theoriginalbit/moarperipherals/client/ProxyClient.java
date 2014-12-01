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

import com.theoriginalbit.moarperipherals.api.upgrade.IUpgradeIcon;
import com.theoriginalbit.moarperipherals.api.upgrade.IUpgradeToolIcon;
import com.theoriginalbit.moarperipherals.common.ProxyCommon;
import com.theoriginalbit.moarperipherals.common.config.ConfigHandler;
import com.theoriginalbit.moarperipherals.common.reference.Constants;
import com.theoriginalbit.moarperipherals.client.render.*;
import com.theoriginalbit.moarperipherals.common.registry.UpgradeRegistry;
import com.theoriginalbit.moarperipherals.common.tile.TileAntennaController;
import com.theoriginalbit.moarperipherals.common.tile.TileKeyboard;
import com.theoriginalbit.moarperipherals.common.tile.TileMiniAntenna;
import com.theoriginalbit.moarperipherals.common.tile.TilePrinter;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import dan200.computercraft.api.turtle.ITurtleUpgrade;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ForgeSubscribe;

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
        float f2 = 16.0F;
        if (volume > 1.0F) {
            f2 *= volume;
        }
        double distanceSq = mc.renderViewEntity.getDistanceSq(x, y, z);
        if (distanceSq < (f2 * f2)) {
            if (delayed && distanceSq > 100.0d) {
                double distance = Math.sqrt(distanceSq) / 40.0d;
                mc.sndManager.func_92070_a(name, (float) x, (float) y, (float) z, volume, pitch, (int) Math.round(distance * 20.0d));
            } else {
                mc.sndManager.playSound(name, (float) x, (float) y, (float) z, volume, pitch);
            }
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
            MinecraftForgeClient.registerItemRenderer(ConfigHandler.blockIdKeyboardMac, rendererKeyboard);
            MinecraftForgeClient.registerItemRenderer(ConfigHandler.blockIdKeyboardPc, rendererKeyboard);
            ClientRegistry.bindTileEntitySpecialRenderer(TileKeyboard.class, rendererKeyboard);
        }

        // Register optional renders when enabled
        if (ConfigHandler.enablePrinter && ConfigHandler.enablePrinterGfx) {
            // printer
            RendererPrinter rendererPrinter = new RendererPrinter();
            MinecraftForgeClient.registerItemRenderer(ConfigHandler.blockIdPrinter, rendererPrinter);
            ClientRegistry.bindTileEntitySpecialRenderer(TilePrinter.class, rendererPrinter);
            // ink cartridge
            MinecraftForgeClient.registerItemRenderer(ConfigHandler.itemIdInkCartridge, new RendererInkCartridge());
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

        if (ConfigHandler.enableMiniAntenna) {
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

    @ForgeSubscribe
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