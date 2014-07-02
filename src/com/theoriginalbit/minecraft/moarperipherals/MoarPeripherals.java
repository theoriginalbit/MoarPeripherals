package com.theoriginalbit.minecraft.moarperipherals;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;

import com.theoriginalbit.minecraft.computercraft.peripheral.PeripheralProvider;
import com.theoriginalbit.minecraft.moarperipherals.block.BlockChatBox;
import com.theoriginalbit.minecraft.moarperipherals.block.BlockIronNote;
import com.theoriginalbit.minecraft.moarperipherals.block.BlockPlayerDetector;
import com.theoriginalbit.minecraft.moarperipherals.reference.Config;
import com.theoriginalbit.minecraft.moarperipherals.reference.ModInfo;
import com.theoriginalbit.minecraft.moarperipherals.reference.Settings;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import dan200.computercraft.api.ComputerCraftAPI;

@Mod(modid = ModInfo.ID, name = ModInfo.NAME, version = ModInfo.VERSION, dependencies = ModInfo.DEPENDENCIES)
@NetworkMod(clientSideRequired = ModInfo.REQUIRED_CLIENT, serverSideRequired = ModInfo.REQUIRED_SERVER)
public class MoarPeripherals {
	
	@Instance(ModInfo.ID)
	public static MoarPeripherals instance;

	public static CreativeTabs creativeTab = new CreativeTabs("tabMoarPeripherals") {
		@Override
		public int getTabIconItemIndex() {
			if (Settings.enableChatBox) {
				return Settings.blockChatBoxID;
			} else if (Settings.enablePlayerDetector) {
				return Settings.blockPlayerDetectorID;
			}
			return Item.skull.itemID;
		}
	};
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		Config.load(event.getSuggestedConfigurationFile());
		MinecraftForge.EVENT_BUS.register(ChatInteceptor.instance);
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event) {
		Blocks.init();
		ComputerCraftAPI.registerPeripheralProvider(new PeripheralProvider());
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {}
	
	public static class Blocks {
		public static BlockChatBox blockChatBox;
		public static BlockPlayerDetector blockPlayerDetector;
		public static BlockIronNote blockIronNote;
		
		public static void init() {
			if (Settings.enablePlayerDetector) { blockPlayerDetector = new BlockPlayerDetector(); }
			if (Settings.enableChatBox) { blockChatBox = new BlockChatBox(); }
			if (Settings.enableIronNote) { blockIronNote = new BlockIronNote(); }
		}
	}

}
