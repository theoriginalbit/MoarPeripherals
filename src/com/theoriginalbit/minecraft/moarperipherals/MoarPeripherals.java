package com.theoriginalbit.minecraft.moarperipherals;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;

import com.theoriginalbit.minecraft.moarperipherals.block.BlockChatBox;
import com.theoriginalbit.minecraft.moarperipherals.block.BlockIronNote;
import com.theoriginalbit.minecraft.moarperipherals.block.BlockPlayerDetector;
import com.theoriginalbit.minecraft.moarperipherals.reference.Config;
import com.theoriginalbit.minecraft.moarperipherals.reference.ModInfo;
import com.theoriginalbit.minecraft.moarperipherals.reference.Settings;
import com.theoriginalbit.minecraft.moarperipherals.tile.TileChatBox;
import com.theoriginalbit.minecraft.moarperipherals.tile.TileChatBox.ChatListener;
import com.theoriginalbit.minecraft.moarperipherals.tile.TileIronNote;
import com.theoriginalbit.minecraft.moarperipherals.tile.TilePlayerDetector;

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
		Config.init(event.getSuggestedConfigurationFile());
		MinecraftForge.EVENT_BUS.register(ChatListener.instance);
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event) {
		Blocks.init();
		ComputerCraftAPI.registerPeripheralProvider(new PeripheralProvider());
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {}
	
	public static class Blocks {
		// we always create this one, its the creative tab icon
		public static BlockChatBox blockChatBox = new BlockChatBox();
		public static BlockPlayerDetector blockPlayerDetector;
		public static BlockIronNote blockIronNote;
		
		public static void init() {
			if (Settings.enablePlayerDetector) {
				blockPlayerDetector = new BlockPlayerDetector();
				GameRegistry.registerBlock(blockPlayerDetector, blockPlayerDetector.getUnlocalizedName());
				GameRegistry.registerTileEntity(TilePlayerDetector.class, "MoarPeripherals Player Detector");
				GameRegistry.addRecipe(new ItemStack(blockPlayerDetector), "SBS", "BRB", "SBS", 'S', Block.stone, 'B', Block.stoneButton, 'R', Item.redstone);
			}
			
			if (Settings.enableChatBox) {
				GameRegistry.registerBlock(blockChatBox, blockChatBox.getUnlocalizedName());
				GameRegistry.registerTileEntity(TileChatBox.class, "MoarPeripherals ChatBox");
				GameRegistry.addRecipe(new ItemStack(blockChatBox), "GGG", "GNG", "GRG", 'G', Item.ingotGold, 'N', Block.music, 'R', Item.redstone);
			}
			
			if (Settings.enableIronNote) {
				blockIronNote = new BlockIronNote();
				GameRegistry.registerBlock(blockIronNote, blockIronNote.getUnlocalizedName());
				GameRegistry.registerTileEntity(TileIronNote.class, "MoarPeripherals Iron Note Block");
				GameRegistry.addRecipe(new ItemStack(blockIronNote), "III", "INI", "IRI", 'I', Item.ingotIron, 'N', Block.music, 'R', Item.redstone);
			}
		}
	}

}
