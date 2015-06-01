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
package com.theoriginalbit.moarperipherals;

import com.theoriginalbit.framework.peripheral.LuaType;
import com.theoriginalbit.framework.peripheral.PeripheralProvider;
import com.theoriginalbit.moarperipherals.api.sorter.Side;
import com.theoriginalbit.moarperipherals.client.CreativeTabMoarPeripherals;
import com.theoriginalbit.moarperipherals.common.ProxyCommon;
import com.theoriginalbit.moarperipherals.common.bitnet.BitNetUniverse;
import com.theoriginalbit.moarperipherals.common.chunk.ChunkLoadingCallback;
import com.theoriginalbit.moarperipherals.common.config.Config;
import com.theoriginalbit.moarperipherals.common.config.ConfigData;
import com.theoriginalbit.moarperipherals.common.handler.ChatBoxHandler;
import com.theoriginalbit.moarperipherals.common.handler.TickHandler;
import com.theoriginalbit.moarperipherals.common.integration.converter.ConverterItemStack;
import com.theoriginalbit.moarperipherals.common.integration.converter.ConverterSide;
import com.theoriginalbit.moarperipherals.common.network.PacketHandler;
import com.theoriginalbit.moarperipherals.common.init.ComputerCraft;
import com.theoriginalbit.moarperipherals.common.reference.ModInfo;
import com.theoriginalbit.moarperipherals.common.init.ModBlocks;
import com.theoriginalbit.moarperipherals.common.init.ModItems;
import com.theoriginalbit.moarperipherals.common.integration.init.UpgradeRegistry;
import com.theoriginalbit.moarperipherals.common.util.LogUtil;
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
    public static MoarPeripherals instance;

    @SidedProxy(clientSide = ModInfo.PROXY_CLIENT, serverSide = ModInfo.PROXY_SERVER)
    public static ProxyCommon proxy;

    public static CreativeTabs creativeTab = new CreativeTabMoarPeripherals();

    public static boolean isServerStopping = false;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        LogUtil.init();

        Config.init(event.getSuggestedConfigurationFile());

        ModItems.INSTANCE.register();
        ModBlocks.INSTANCE.register();
        UpgradeRegistry.INSTANCE.register();

        proxy.preInit();

        ChatBoxHandler.init();
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        PacketHandler.init();

        if (ConfigData.shouldChunkLoad()) {
            LogUtil.debug("Registering chunk loading callback");
            ForgeChunkManager.setForcedChunkLoadingCallback(instance, new ChunkLoadingCallback());
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

        ModItems.INSTANCE.addRecipes();
        ModBlocks.INSTANCE.addRecipes();

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
