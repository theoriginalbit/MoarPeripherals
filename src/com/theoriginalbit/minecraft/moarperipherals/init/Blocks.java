package com.theoriginalbit.minecraft.moarperipherals.init;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import com.theoriginalbit.minecraft.moarperipherals.block.*;
import com.theoriginalbit.minecraft.moarperipherals.item.ItemBlockKeyboard;
import com.theoriginalbit.minecraft.moarperipherals.reference.ModInfo;
import com.theoriginalbit.minecraft.moarperipherals.reference.Settings;
import com.theoriginalbit.minecraft.moarperipherals.tile.TileChatBox;
import com.theoriginalbit.minecraft.moarperipherals.tile.TileIronNote;
import com.theoriginalbit.minecraft.moarperipherals.tile.TileKeyboard;
import com.theoriginalbit.minecraft.moarperipherals.tile.TilePlayerDetector;

import cpw.mods.fml.common.registry.GameRegistry;

public final class Blocks {
	public static BlockGeneric blockChatBox;
	public static BlockGeneric blockPlayerDetector;
	public static BlockGeneric blockIronNote;
	public static BlockGeneric blockKeyboard;
	
	public static void init() {
		if (Settings.enablePlayerDetector) {
			blockPlayerDetector = new BlockPlayerDetector();
			GameRegistry.registerBlock(blockPlayerDetector, blockPlayerDetector.getUnlocalizedName());
			GameRegistry.registerTileEntity(TilePlayerDetector.class, ModInfo.ID + ":tilePlayerDetector");
		}
		if (Settings.enableChatBox) {
			blockChatBox = new BlockChatBox();
			GameRegistry.registerBlock(blockChatBox, blockChatBox.getUnlocalizedName());
			GameRegistry.registerTileEntity(TileChatBox.class, ModInfo.ID + ":tileChatBox");
		}
		if (Settings.enableIronNote) {
			blockIronNote = new BlockIronNote();
			GameRegistry.registerBlock(blockIronNote, blockIronNote.getUnlocalizedName());
			GameRegistry.registerTileEntity(TileIronNote.class, ModInfo.ID + ":tileIronNote");
		}
		if (Settings.enableKeyboard) {
			blockKeyboard = new BlockKeyboard();
			GameRegistry.registerBlock(blockKeyboard, ItemBlockKeyboard.class, blockKeyboard.getUnlocalizedName());
			GameRegistry.registerTileEntity(TileKeyboard.class, ModInfo.ID + ":tileKeyboard");
		}
	}
	
	public static void initRecipes() {
		if (Settings.enablePlayerDetector) {
			GameRegistry.addRecipe(new ItemStack(blockPlayerDetector), "SBS", "BRB", "SBS", 'S', Block.stone, 'B', Block.stoneButton, 'R', Item.redstone);
		}
		if (Settings.enableChatBox) {
			GameRegistry.addRecipe(new ItemStack(blockChatBox), "GGG", "GNG", "GRG", 'G', Item.ingotGold, 'N', Block.music, 'R', Item.redstone);
		}
		if (Settings.enableIronNote) {
			GameRegistry.addRecipe(new ItemStack(blockIronNote), "III", "INI", "IRI", 'I', Item.ingotIron, 'N', Block.music, 'R', Item.redstone);
		}
		if (Settings.enableKeyboard) {
			GameRegistry.addRecipe(new ItemStack(blockKeyboard), "KKK", 'K', Items.itemKeyboardPart);
		}
	}
	
	public static void oreRegistration() {
		OreDictionary.registerOre("peripheralChatbox", blockChatBox);
		OreDictionary.registerOre("peripheralPlayerDetector", blockPlayerDetector);
		OreDictionary.registerOre("peripheralIronNote", blockIronNote);
		OreDictionary.registerOre("peripheralKeyboard", blockKeyboard);
	}
}