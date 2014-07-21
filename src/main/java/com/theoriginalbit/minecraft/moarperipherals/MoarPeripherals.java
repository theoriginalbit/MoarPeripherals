package com.theoriginalbit.minecraft.moarperipherals;

import com.theoriginalbit.minecraft.computercraft.peripheral.PeripheralProvider;
import com.theoriginalbit.minecraft.moarperipherals.handler.*;
import com.theoriginalbit.minecraft.moarperipherals.init.Blocks;
import com.theoriginalbit.minecraft.moarperipherals.init.Fluids;
import com.theoriginalbit.minecraft.moarperipherals.init.Items;
import com.theoriginalbit.minecraft.moarperipherals.interfaces.IProxy;
import com.theoriginalbit.minecraft.moarperipherals.reference.ModInfo;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import dan200.computercraft.api.ComputerCraftAPI;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.common.MinecraftForge;

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
@Mod(modid = ModInfo.ID, name = ModInfo.NAME, version = ModInfo.VERSION, dependencies = ModInfo.DEPENDENCIES)
@NetworkMod(channels = {"moarp"}, clientSideRequired = ModInfo.REQUIRED_CLIENT, serverSideRequired = ModInfo.REQUIRED_SERVER, packetHandler = PacketHandler.class)
public class MoarPeripherals {

    @Instance(ModInfo.ID)
    public static MoarPeripherals instance;

    @SidedProxy(clientSide = ModInfo.PROXY_CLIENT, serverSide = ModInfo.PROXY_SERVER)
    public static IProxy proxy;

    public static GuiHandler guiHandler = new GuiHandler();

    public static CreativeTabs creativeTab = new CreativeTabMoarPeripheral();

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        ConfigurationHandler.init(event.getSuggestedConfigurationFile());

        MinecraftForge.EVENT_BUS.register(ChatHandler.instance);
        MinecraftForge.EVENT_BUS.register(BucketHandler.instance);
        NetworkRegistry.instance().registerGuiHandler(this, guiHandler);
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        Blocks.init();
        Items.init();
        Fluids.init();

        Blocks.initRecipes();
        Items.initRecipes();

        Blocks.oreRegistration();
        Items.oreRegistration();
        Fluids.oreRegistration();

        proxy.registerRenderInfo();
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        ComputerCraftAPI.registerPeripheralProvider(new PeripheralProvider());
    }

}