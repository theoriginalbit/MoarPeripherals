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
package com.theoriginalbit.moarperipherals.common;

import com.theoriginalbit.moarperipherals.MoarPeripherals;
import com.theoriginalbit.moarperipherals.client.gui.GuiHandler;
import com.theoriginalbit.moarperipherals.common.handler.ChatBoxHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import org.apache.commons.lang3.ArrayUtils;

public class ProxyCommon {

    public void preInit() {
        MinecraftForge.EVENT_BUS.register(ChatBoxHandler.instance);
        NetworkRegistry.INSTANCE.registerGuiHandler(MoarPeripherals.instance, new GuiHandler());
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
        return ArrayUtils.contains(server.getConfigurationManager().func_152606_n(), player.getDisplayName().toLowerCase());
    }

}