package com.theoriginalbit.minecraft.moarperipherals;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import com.theoriginalbit.minecraft.moarperipherals.playerdetector.BlockPlayerDetector;
import com.theoriginalbit.minecraft.moarperipherals.playerdetector.TilePlayerDetector;
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
import cpw.mods.fml.common.registry.GameRegistry;
import dan200.computercraft.api.ComputerCraftAPI;

@Mod(modid = ModInfo.ID, name = ModInfo.NAME, version = ModInfo.VERSION, dependencies = ModInfo.DEPENDENCIES)
@NetworkMod(clientSideRequired = ModInfo.REQUIRED_CLIENT, serverSideRequired = ModInfo.REQUIRED_SERVER)
public class MoarPeripherals {
	
	@Instance(ModInfo.ID)
	MoarPeripherals instance;

	public static final CreativeTabs creativeTab = new CreativeTabs("tabMoarPeripherals") {
		@Override
		public int getTabIconItemIndex() {
			return Settings.blockPlayerDetectorID;
		}
	};
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		Config.init(event.getSuggestedConfigurationFile());
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event) {
		Blocks.init();
		ComputerCraftAPI.registerPeripheralProvider(new PeripheralProvider());
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {}
	
	public static class Blocks {
		public static BlockPlayerDetector blockPlayerDetector;
		
		public static void init() {
			if (Settings.enablePlayerDetector) {
				blockPlayerDetector = new BlockPlayerDetector();
				GameRegistry.registerBlock(blockPlayerDetector, blockPlayerDetector.getUnlocalizedName());
				GameRegistry.registerTileEntity(TilePlayerDetector.class, "MoarPeripherals Player Detector");
				GameRegistry.addRecipe(new ItemStack(blockPlayerDetector), "SBS", "BRB", "SBS", 'S', Block.stone, 'B', Block.stoneButton, 'R', Item.redstone);
			}
		}
	}

}
