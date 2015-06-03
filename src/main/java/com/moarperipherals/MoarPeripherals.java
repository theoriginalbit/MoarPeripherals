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
package com.moarperipherals;

import com.theoriginalbit.framework.peripheral.LuaType;
import com.theoriginalbit.framework.peripheral.PeripheralProvider;
import com.moarperipherals.tile.sorter.Side;
import com.moarperipherals.creativetab.CreativeTabMoarPeripherals;
import com.moarperipherals.proxy.ProxyCommon;
import com.moarperipherals.bitnet.BitNetUniverse;
import com.moarperipherals.world.chunk.ChunkLoadingCallback;
import com.moarperipherals.config.Config;
import com.moarperipherals.config.ConfigData;
import com.moarperipherals.handler.ChatBoxHandler;
import com.moarperipherals.handler.TickHandler;
import com.moarperipherals.integration.init.ComputerCraft;
import com.moarperipherals.init.ModBlocks;
import com.moarperipherals.init.ModItems;
import com.moarperipherals.integration.converter.ConverterItemStack;
import com.moarperipherals.integration.converter.ConverterSide;
import com.moarperipherals.integration.registry.UpgradeRegistry;
import com.moarperipherals.network.PacketHandler;
import com.moarperipherals.util.LogUtil;
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

@Mod(modid = ModInfo.ID, name = ModInfo.NAME, version = ModInfo.VERSION, dependencies = ModInfo.DEPENDENCIES)
public class MoarPeripherals {
    @Instance(ModInfo.ID)
    public static MoarPeripherals INSTANCE;

    @SidedProxy(clientSide = ModInfo.PROXY_CLIENT, serverSide = ModInfo.PROXY_SERVER)
    public static ProxyCommon proxy;

    public static CreativeTabs creativeTab = new CreativeTabMoarPeripherals();

    public static boolean isServerStopping = false;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        LogUtil.init();

        Config.init(event.getSuggestedConfigurationFile());

        ModItems.register();
        ModBlocks.register();
        UpgradeRegistry.register();

        proxy.preInit();

        ChatBoxHandler.init();
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        PacketHandler.init();

        if (ConfigData.shouldChunkLoad()) {
            LogUtil.debug("Registering chunk loading callback");
            ForgeChunkManager.setForcedChunkLoadingCallback(INSTANCE, new ChunkLoadingCallback());
        }

        if (ConfigData.enableAntenna) {
            LogUtil.debug("Registering BitNet tick handler");
            FMLCommonHandler.instance().bus().register(BitNetUniverse.UNIVERSE);
        }

        if (TickHandler.shouldRegister()) {
            LogUtil.debug("Registering server tick handler");
            FMLCommonHandler.instance().bus().register(TickHandler.INSTANCE);
        }

        proxy.init();
        proxy.registerRenderInfo();
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit();
        ComputerCraft.init();

        ModItems.addRecipes();
        ModBlocks.addRecipes();

        LuaType.registerTypeConverter(new ConverterItemStack());
        LuaType.registerClassToNameMapping(ItemStack.class, "item");
        LuaType.registerTypeConverter(new ConverterSide());
        LuaType.registerClassToNameMapping(Side.class, "side");

        ComputerCraftAPI.registerPeripheralProvider(new PeripheralProvider());
    }

    @EventHandler
    public void serverStopping(FMLServerStoppingEvent e) {
        isServerStopping = true;
    }
}
