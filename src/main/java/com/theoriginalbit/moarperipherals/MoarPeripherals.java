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
package com.theoriginalbit.moarperipherals;

import com.theoriginalbit.moarperipherals.api.peripheral.LuaType;
import com.theoriginalbit.moarperipherals.api.peripheral.PeripheralProvider;
import com.theoriginalbit.moarperipherals.common.config.ConfigHandler;
import com.theoriginalbit.moarperipherals.common.handler.ChatBoxHandler;
import com.theoriginalbit.moarperipherals.common.ProxyCommon;
import com.theoriginalbit.moarperipherals.common.handler.TickHandler;
import com.theoriginalbit.moarperipherals.common.network.PacketHandler;
import com.theoriginalbit.moarperipherals.common.registry.BitNetRegistry;
import com.theoriginalbit.moarperipherals.common.registry.ModBlocks;
import com.theoriginalbit.moarperipherals.common.registry.ModItems;
import com.theoriginalbit.moarperipherals.common.registry.UpgradeRegistry;
import com.theoriginalbit.moarperipherals.common.CreativeTabMoarPeripherals;
import com.theoriginalbit.moarperipherals.common.converter.ConverterItemStack;
import com.theoriginalbit.moarperipherals.common.reference.ComputerCraftInfo;
import com.theoriginalbit.moarperipherals.common.reference.ModInfo;
import com.theoriginalbit.moarperipherals.common.utils.LogUtils;
import com.theoriginalbit.moarperipherals.common.chunk.ChunkLoadingCallback;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStoppingEvent;
import dan200.computercraft.api.ComputerCraftAPI;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ForgeChunkManager;

@Mod(modid = ModInfo.ID, name = ModInfo.NAME, version = ModInfo.VERSION, dependencies = ModInfo.DEPENDENCIES, guiFactory = ModInfo.GUI_FACTORY)
public class MoarPeripherals {

    @Instance(ModInfo.ID)
    public static MoarPeripherals instance;

    @SidedProxy(clientSide = ModInfo.PROXY_CLIENT, serverSide = ModInfo.PROXY_SERVER)
    public static ProxyCommon proxy;

    public static CreativeTabs creativeTab = new CreativeTabMoarPeripherals();

    public static boolean isServerStopping = false;

    @EventHandler
    @SuppressWarnings("unused")
    public void preInit(FMLPreInitializationEvent event) {
        LogUtils.init();

        final ConfigHandler config = ConfigHandler.init(event.getSuggestedConfigurationFile());
        FMLCommonHandler.instance().bus().register(config);

        proxy.preInit();

        ChatBoxHandler.init();
    }

    @EventHandler
    @SuppressWarnings("unused")
    public void init(FMLInitializationEvent event) {
        PacketHandler.init();

        ModItems.INSTANCE.register();
        ModBlocks.INSTANCE.register();
        UpgradeRegistry.INSTANCE.register();

        if (ConfigHandler.shouldChunkLoad()) {
            LogUtils.debug("Registering chunk loading callback");
            ForgeChunkManager.setForcedChunkLoadingCallback(instance, new ChunkLoadingCallback());
        }

        if (ConfigHandler.enableAntenna) {
            LogUtils.debug("Registering BitNet tick handler");
            FMLCommonHandler.instance().bus().register(new BitNetRegistry());
        }

        if (TickHandler.shouldRegister()) {
            LogUtils.debug("Registering server tick handler");
            FMLCommonHandler.instance().bus().register(TickHandler.INSTANCE);
        }

        proxy.init();
        proxy.registerRenderInfo();
    }

    @EventHandler
    @SuppressWarnings("unused")
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit();
        ComputerCraftInfo.init();

        ModItems.INSTANCE.addRecipes();
        ModBlocks.INSTANCE.addRecipes();

        LuaType.registerTypeConverter(new ConverterItemStack());
        LuaType.registerClassToNameMapping(ItemStack.class, "item");

        ComputerCraftAPI.registerPeripheralProvider(new PeripheralProvider());
    }

    @EventHandler
    @SuppressWarnings("unused")
    public void serverStopping(FMLServerStoppingEvent e) {
        isServerStopping = true;
    }

}
