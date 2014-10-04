/**
 * Copyright (c) 2013-2014, Joshua Asbury (@theoriginalbit)
 * http://wiki.theoriginalbit.com/moarperipherals/
 *
 * MoarPeripherals is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://www.mod-buildcraft.com/MMPL-1.0.txt
 */
package com.theoriginalbit.minecraft.moarperipherals.common;

import com.theoriginalbit.minecraft.moarperipherals.client.gui.GuiHandler;
import com.theoriginalbit.minecraft.moarperipherals.common.handler.ChatHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

public class ProxyCommon {

    public void preInit() {
        MinecraftForge.EVENT_BUS.register(ChatHandler.instance);
        NetworkRegistry.instance().registerGuiHandler(this, new GuiHandler());
    }

    public void init() {

    }

    public void postInit() {

    }

    public World getClientWorld(int dimId) {
        return MinecraftServer.getServer().worldServerForDimension(dimId);
    }

    public boolean isClient() {
        return false;
    }

    public void registerRenderInfo() {
    }

    public boolean isOp(EntityPlayer player) {
        MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
        return server.getConfigurationManager().getOps().contains(player.username.trim().toLowerCase());
    }

}