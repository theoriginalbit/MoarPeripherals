package com.theoriginalbit.minecraft.moarperipherals;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.common.MinecraftForge;

import com.theoriginalbit.minecraft.computercraft.peripheral.PeripheralProvider;
import com.theoriginalbit.minecraft.moarperipherals.block.Blocks;
import com.theoriginalbit.minecraft.moarperipherals.fluid.Fluids;
import com.theoriginalbit.minecraft.moarperipherals.handler.BucketHandler;
import com.theoriginalbit.minecraft.moarperipherals.handler.ChatHandler;
import com.theoriginalbit.minecraft.moarperipherals.handler.ConfigurationHandler;
import com.theoriginalbit.minecraft.moarperipherals.interfaces.IProxy;
import com.theoriginalbit.minecraft.moarperipherals.item.Items;
import com.theoriginalbit.minecraft.moarperipherals.network.TinyPacketHandler;
import com.theoriginalbit.minecraft.moarperipherals.reference.ModInfo;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import dan200.computercraft.api.ComputerCraftAPI;

@Mod(modid = ModInfo.ID, name = ModInfo.NAME, version = ModInfo.VERSION, dependencies = ModInfo.DEPENDENCIES)
@NetworkMod(clientSideRequired = ModInfo.REQUIRED_CLIENT, serverSideRequired = ModInfo.REQUIRED_SERVER, tinyPacketHandler = TinyPacketHandler.class)
public class MoarPeripherals {
	
	@Instance(ModInfo.ID)
	public static MoarPeripherals instance;
	
	@SidedProxy(clientSide = ModInfo.PROXY_CLIENT, serverSide = ModInfo.PROXY_SERVER)
	public static IProxy proxy;

	public static CreativeTabs creativeTab = new CreativeTabMoarPeripheral();
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		ConfigurationHandler.init(event.getSuggestedConfigurationFile());
		
		MinecraftForge.EVENT_BUS.register(ChatHandler.instance);
		MinecraftForge.EVENT_BUS.register(BucketHandler.instance);
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event) {
		Blocks.init();
		Items.init();
		Fluids.init();
		
		proxy.registerRenderInfo();
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		ComputerCraftAPI.registerPeripheralProvider(new PeripheralProvider());
	}
}
