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

import com.moarperipherals.MoarPeripherals;
import com.moarperipherals.client.gui.GuiHandler;
import com.moarperipherals.handler.ChatBoxHandler;
import com.moarperipherals.ModInfo;
import com.moarperipherals.util.ResourceExtractingUtil;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.ModContainer;
import cpw.mods.fml.common.network.NetworkRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ArrayUtils;

import java.io.File;
import java.io.IOException;

public class ProxyCommon {

    public void preInit() {
        MinecraftForge.EVENT_BUS.register(ChatBoxHandler.INSTANCE);
        FMLCommonHandler.instance().bus().register(ChatBoxHandler.INSTANCE);
        NetworkRegistry.INSTANCE.registerGuiHandler(MoarPeripherals.INSTANCE, new GuiHandler());
    }

    public void init() {
        setupLuaFiles();
    }

    public void postInit() {
        // NO-OP
    }

    public World getClientWorld(int dimId) {
        return MinecraftServer.getServer().worldServerForDimension(dimId);
    }

    public void playSound(double x, double y, double z, String name, float volume, float pitch, boolean delayed) {
        // NO-OP
    }

    public boolean isClient() {
        return false;
    }

    public void registerRenderInfo() {
        // NO-OP
    }

    public boolean isOp(EntityPlayer player) {
        MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
        return ArrayUtils.contains(server.getConfigurationManager().func_152606_n(), player.getDisplayName().toLowerCase());
    }

    public File getBase() {
        return new File(".");
    }

    /*
     * Code modified from OpenCCSensors: https://github.com/Cloudhunter/OpenCCSensors
     */
    private boolean setupLuaFiles() {
        final ModContainer container = FMLCommonHandler.instance().findContainerFor(MoarPeripherals.INSTANCE);
        final File modFile = container.getSource();
        try {
            if (modFile.isDirectory()) {
                final File destFile = new File(getBase(), ModInfo.EXTRACTED_LUA_PATH);
                if (destFile.exists()) {
                    FileUtils.deleteDirectory(destFile);
                }
                ResourceExtractingUtil.copy(new File(modFile, ModInfo.LUA_PATH), destFile);
            } else {
                ResourceExtractingUtil.extractZipToLocation(modFile, ModInfo.LUA_PATH, ModInfo.EXTRACTED_LUA_PATH);
            }
        } catch (IOException ignored) {
        }
        return true;
    }

}