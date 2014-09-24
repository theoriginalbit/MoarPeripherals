/**
 * Copyright (c) 2013-2014, Joshua Asbury (@theoriginalbit)
 * http://wiki.theoriginalbit.com/moarperipherals/
 *
 * MoarPeripherals is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://www.mod-buildcraft.com/MMPL-1.0.txt
 */
package com.theoriginalbit.moarperipherals;

import com.theoriginalbit.framework.peripheral.LuaType;
import com.theoriginalbit.framework.peripheral.PeripheralProvider;
import com.theoriginalbit.moarperipherals.common.ProxyCommon;
import com.theoriginalbit.moarperipherals.server.chunk.ChunkLoadingCallback;
import com.theoriginalbit.moarperipherals.common.CreativeTabMoarPeripherals;
import com.theoriginalbit.moarperipherals.common.converters.ConverterItemStack;
import com.theoriginalbit.moarperipherals.dictionary.ItemSearch;
import com.theoriginalbit.moarperipherals.common.handler.*;
import com.theoriginalbit.moarperipherals.reference.ComputerCraftInfo;
import com.theoriginalbit.moarperipherals.reference.Settings;
import com.theoriginalbit.moarperipherals.common.registry.*;
import com.theoriginalbit.moarperipherals.reference.ModInfo;
import com.theoriginalbit.moarperipherals.utils.LogUtils;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStoppingEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import dan200.computercraft.api.ComputerCraftAPI;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.MinecraftForge;

@Mod(modid = ModInfo.ID, name = ModInfo.NAME, version = ModInfo.VERSION, dependencies = ModInfo.DEPENDENCIES)
public class MoarPeripherals {

    @Instance(ModInfo.ID)
    public static MoarPeripherals instance;

    @SidedProxy(clientSide = ModInfo.PROXY_CLIENT, serverSide = ModInfo.PROXY_SERVER)
    public static ProxyCommon proxy;

    public static SimpleNetworkWrapper networkWrapper;

    public static GuiHandler guiHandler = new GuiHandler();

    public static CreativeTabs creativeTab = new CreativeTabMoarPeripherals();

    public static boolean isServerStopping = false;

    @EventHandler
    @SuppressWarnings("unused")
    public void preInit(FMLPreInitializationEvent event) {
        LogUtils.init();

        networkWrapper = NetworkRegistry.INSTANCE.newSimpleChannel(ModInfo.CHANNEL);

        ConfigHandler.init(event.getSuggestedConfigurationFile());
        ChatHandler.init();

        MinecraftForge.EVENT_BUS.register(ChatHandler.instance);
        NetworkRegistry.INSTANCE.registerGuiHandler(this, guiHandler);
    }

    @EventHandler
    @SuppressWarnings("unused")
    public void init(FMLInitializationEvent event) {
        MaterialRegistry.init();
        BlockRegistry.init();
        ItemRegistry.init();

        MaterialRegistry.oreRegistration();
        BlockRegistry.oreRegistration();
        ItemRegistry.oreRegistration();

        if (Settings.shouldChunkLoad()) {
            LogUtils.debug("Registering chunk loading callback");
            ForgeChunkManager.setForcedChunkLoadingCallback(instance, new ChunkLoadingCallback());
        }

        if (Settings.enableAntenna) {
            LogUtils.debug("Registering BitNet tick handler");
            TickRegistry.registerTickHandler(new BitNetRegistry(), Side.SERVER);
        }

        proxy.registerRenderInfo();
    }

    @EventHandler
    @SuppressWarnings("unused")
    public void postInit(FMLPostInitializationEvent event) {
        ItemSearch.init();

        ComputerCraftInfo.init();
        RecipeRegistry.init();

        LuaType.registerTypeConverter(new ConverterItemStack());
        LuaType.registerClassToNameMapping(ItemStack.class, "item");

        UpgradeRegistry.init();

        ComputerCraftAPI.registerPeripheralProvider(new PeripheralProvider());
    }

    @EventHandler
    @SuppressWarnings("unused")
    public void serverStopping(FMLServerStoppingEvent e) {
        isServerStopping = true;
    }

}
