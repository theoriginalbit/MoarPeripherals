/**
 * Copyright (c) 2013-2014, Joshua Asbury (@theoriginalbit)
 * http://wiki.theoriginalbit.com/moarperipherals/
 *
 * MoarPeripherals is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://www.mod-buildcraft.com/MMPL-1.0.txt
 */
package com.theoriginalbit.minecraft.moarperipherals;

import com.theoriginalbit.minecraft.framework.peripheral.LuaType;
import com.theoriginalbit.minecraft.framework.peripheral.PeripheralProvider;
import com.theoriginalbit.minecraft.moarperipherals.common.CreativeTabMoarPeripheral;
import com.theoriginalbit.minecraft.moarperipherals.common.ProxyCommon;
import com.theoriginalbit.minecraft.moarperipherals.common.config.ConfigHandler;
import com.theoriginalbit.minecraft.moarperipherals.common.network.PacketHandler;
import com.theoriginalbit.minecraft.moarperipherals.server.chunk.ChunkLoadingCallback;
import com.theoriginalbit.minecraft.moarperipherals.common.converters.ConverterItemStack;
import com.theoriginalbit.minecraft.moarperipherals.common.handler.*;
import com.theoriginalbit.minecraft.moarperipherals.common.reference.ComputerCraftInfo;
import com.theoriginalbit.minecraft.moarperipherals.common.registry.*;
import com.theoriginalbit.minecraft.moarperipherals.common.reference.ModInfo;
import com.theoriginalbit.minecraft.moarperipherals.common.utils.LogUtils;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStoppingEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;
import dan200.computercraft.api.ComputerCraftAPI;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ForgeChunkManager;

@Mod(modid = ModInfo.ID, name = ModInfo.NAME, version = ModInfo.VERSION, dependencies = ModInfo.DEPENDENCIES)
@NetworkMod(channels = {"moarp"}, clientSideRequired = ModInfo.REQUIRED_CLIENT, serverSideRequired = ModInfo.REQUIRED_SERVER, packetHandler = PacketHandler.class)
public class MoarPeripherals {

    @Instance(ModInfo.ID)
    public static MoarPeripherals instance;

    @SidedProxy(clientSide = ModInfo.PROXY_CLIENT, serverSide = ModInfo.PROXY_SERVER)
    public static ProxyCommon proxy;

    public static CreativeTabs creativeTab = new CreativeTabMoarPeripheral();

    public static boolean isServerStopping = false;

    @EventHandler
    @SuppressWarnings("unused")
    public void preInit(FMLPreInitializationEvent event) {
        LogUtils.init();

        ConfigHandler.init(event.getSuggestedConfigurationFile());

        proxy.preInit();

        ChatHandler.init();
    }

    @EventHandler
    @SuppressWarnings("unused")
    public void init(FMLInitializationEvent event) {
        ModItems.INSTANCE.register();
        ModBlocks.INSTANCE.register();

        if (ConfigHandler.shouldChunkLoad()) {
            LogUtils.debug("Registering chunk loading callback");
            ForgeChunkManager.setForcedChunkLoadingCallback(instance, new ChunkLoadingCallback());
        }

        if (ConfigHandler.enableAntenna) {
            LogUtils.debug("Registering BitNet tick handler");
            TickRegistry.registerTickHandler(new BitNetRegistry(), Side.SERVER);
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
