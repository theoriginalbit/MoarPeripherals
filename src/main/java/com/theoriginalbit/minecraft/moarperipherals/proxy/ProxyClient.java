package com.theoriginalbit.minecraft.moarperipherals.proxy;

import com.theoriginalbit.minecraft.moarperipherals.interfaces.IProxy;
import com.theoriginalbit.minecraft.moarperipherals.reference.Settings;
import com.theoriginalbit.minecraft.moarperipherals.render.RendererItemInkCartridge;
import com.theoriginalbit.minecraft.moarperipherals.render.RendererItemSonic;
import com.theoriginalbit.minecraft.moarperipherals.render.RendererKeyboard;
import com.theoriginalbit.minecraft.moarperipherals.render.RendererPrinter;
import com.theoriginalbit.minecraft.moarperipherals.tile.TileKeyboard;
import com.theoriginalbit.minecraft.moarperipherals.tile.TilePrinter;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;

/**
 * A Minecraft mod that adds more peripherals into the ComputerCraft mod.
 * Official Thread:
 * http://www.computercraft.info/forums2/index.php?/topic/19357-
 * Official Wiki:
 * http://wiki.theoriginalbit.com/moarperipherals/
 *
 * Copyright (C) 2014  Joshua Asbury (@theoriginalbit)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/.
 */
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
    public boolean isClient() {
        return FMLCommonHandler.instance().getEffectiveSide().isClient();
    }

    @Override
    public void registerRenderInfo() {
        // Register Keyboard renderers
        if (Settings.enableKeyboard) {
            RendererKeyboard rendererKeyboard = new RendererKeyboard();
            MinecraftForgeClient.registerItemRenderer(Settings.blockIdKeyboard, rendererKeyboard);
            ClientRegistry.bindTileEntitySpecialRenderer(TileKeyboard.class, rendererKeyboard);
        }

        // Register optional renders when enabled
        if (Settings.enablePrinter && Settings.enablePrinterGfx) {
            // printer
            RendererPrinter rendererPrinter = new RendererPrinter();
            MinecraftForgeClient.registerItemRenderer(Settings.blockIdPrinter, rendererPrinter);
            ClientRegistry.bindTileEntitySpecialRenderer(TilePrinter.class, rendererPrinter);
            // ink cartridge
            MinecraftForgeClient.registerItemRenderer(Settings.itemIdInkCartridge, new RendererItemInkCartridge());
        }

        if (Settings.isSonicEnabled() && Settings.enableSonicGfx) {
            MinecraftForgeClient.registerItemRenderer(Settings.itemIdSonic, new RendererItemSonic());
        }

    }

    @Override
    public boolean isOp(EntityPlayer player) {
        return false;
    }

}