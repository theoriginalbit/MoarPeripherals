package com.theoriginalbit.minecraft.moarperipherals.init;

import com.theoriginalbit.minecraft.moarperipherals.block.*;
import com.theoriginalbit.minecraft.moarperipherals.itemblock.ItemBlockKeyboard;
import com.theoriginalbit.minecraft.moarperipherals.reference.ModInfo;
import com.theoriginalbit.minecraft.moarperipherals.reference.Settings;
import com.theoriginalbit.minecraft.moarperipherals.tile.*;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.oredict.OreDictionary;

public final class Blocks {

    public static BlockGeneric blockChatBox;
    public static BlockGeneric blockPlayerDetector;
    public static BlockGeneric blockIronNote;
    public static BlockGeneric blockKeyboard;
    public static BlockGeneric blockPrinter;

    public static void init() {
        if (Settings.enablePlayerDetector) {
            blockPlayerDetector = new BlockPlayerDetector();
            registerBlock(blockPlayerDetector, TilePlayerDetector.class, "tilePlayerDetector");
        }
        if (Settings.enableChatBox) {
            blockChatBox = new BlockChatBox();
            registerBlock(blockChatBox, TileChatBox.class, "tileChatBox");
        }
        if (Settings.enableIronNote) {
            blockIronNote = new BlockIronNote();
            registerBlock(blockIronNote, TileIronNote.class, "tileIronNote");
        }
        if (Settings.enableKeyboard) {
            blockKeyboard = new BlockKeyboard();
            registerBlock(blockKeyboard, TileKeyboard.class, "tileKeyboard", ItemBlockKeyboard.class);
        }
        if (Settings.enablePrinter) {
            blockPrinter = new BlockPrinter();
            registerBlock(blockPrinter, TilePrinter.class, "tilePrinter");
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

    private static void registerBlock(BlockGeneric block, Class<? extends TileEntity> tile, String teName) {
        registerBlock(block, tile, teName, null);
    }

    private static void registerBlock(BlockGeneric block, Class<? extends TileEntity> tile, String teName, Class<? extends ItemBlock> itemBlock) {
        if (itemBlock != null) {
            GameRegistry.registerBlock(block, itemBlock, block.getUnlocalizedName());
        } else {
            GameRegistry.registerBlock(block, block.getUnlocalizedName());
        }
        GameRegistry.registerTileEntity(tile, ModInfo.ID + ':' + teName);
    }

}