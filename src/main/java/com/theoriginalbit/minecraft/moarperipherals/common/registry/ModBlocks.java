/**
 * Copyright (c) 2013-2014, Joshua Asbury (@theoriginalbit)
 * http://wiki.theoriginalbit.com/moarperipherals/
 *
 * MoarPeripherals is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://www.mod-buildcraft.com/MMPL-1.0.txt
 */
package com.theoriginalbit.minecraft.moarperipherals.common.registry;

import com.theoriginalbit.minecraft.moarperipherals.common.block.*;
import com.theoriginalbit.minecraft.moarperipherals.common.config.ConfigHandler;
import com.theoriginalbit.minecraft.moarperipherals.common.item.block.ItemBlockKeyboard;
import com.theoriginalbit.minecraft.moarperipherals.common.reference.ComputerCraftInfo;
import com.theoriginalbit.minecraft.moarperipherals.common.reference.ModInfo;
import com.theoriginalbit.minecraft.moarperipherals.common.tile.*;
import com.theoriginalbit.minecraft.moarperipherals.common.upgrades.UpgradeChatBox;
import com.theoriginalbit.minecraft.moarperipherals.common.upgrades.UpgradeDictionary;
import com.theoriginalbit.minecraft.moarperipherals.common.upgrades.UpgradeIronNote;
import cpw.mods.fml.common.registry.GameRegistry;
import dan200.computercraft.api.ComputerCraftAPI;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;

/**
 * @author theoriginalbit
 * @since 4/10/2014
 */
public class ModBlocks {

    public static final ModBlocks INSTANCE = new ModBlocks();

    private ModBlocks() {
        // prevent other instances being constructed
    }

    public static Block blockChatBox, blockPlayerDetector, blockIronNote, blockKeyboard, blockPrinter,
            blockDictionary, blockAntenna, blockAntennaCell, blockAntennaMiniCell, blockAntennaController;

    public final void register() {
        if (ConfigHandler.enablePlayerDetector) {
            blockPlayerDetector = new BlockPlayerDetector();
            GameRegistry.registerBlock(blockPlayerDetector, blockPlayerDetector.getUnlocalizedName());
            GameRegistry.registerTileEntity(TilePlayerDetector.class, ModInfo.ID + ":tilePlayerDetector");

            // add the player detector to the ore dictionary as per request from tattyseal
            OreDictionary.registerOre("peripheralPlayerDetector", blockPlayerDetector);
        }

        if (ConfigHandler.enableChatBox) {
            blockChatBox = new BlockChatBox();
            GameRegistry.registerBlock(blockChatBox, blockChatBox.getUnlocalizedName());
            GameRegistry.registerTileEntity(TileChatBox.class, ModInfo.ID + ":tileChatBox");
            ComputerCraftAPI.registerTurtleUpgrade(new UpgradeChatBox());
        }

        if (ConfigHandler.enableIronNote) {
            blockIronNote = new BlockIronNote();
            GameRegistry.registerBlock(blockIronNote, blockIronNote.getUnlocalizedName());
            GameRegistry.registerTileEntity(TileIronNote.class, ModInfo.ID + ":tileIronNote");
            ComputerCraftAPI.registerTurtleUpgrade(new UpgradeIronNote());
        }

        if (ConfigHandler.enableKeyboard) {
            blockKeyboard = new BlockKeyboard();
            GameRegistry.registerBlock(blockKeyboard, ItemBlockKeyboard.class, blockKeyboard.getUnlocalizedName());
            GameRegistry.registerTileEntity(TileKeyboard.class, ModInfo.ID + ":tileKeyboard");
        }

        if (ConfigHandler.enablePrinter) {
            blockPrinter = new BlockPrinter();
//            GameRegistry.registerBlock(blockPrinter, blockPrinter.getUnlocalizedName());
//            GameRegistry.registerTileEntity(TilePrinter.class, ModInfo.ID + ":tilePrinter");
        }

        if (ConfigHandler.enableDictionary) {
            blockDictionary = new BlockDictionary();
            GameRegistry.registerBlock(blockDictionary, blockDictionary.getUnlocalizedName());
            GameRegistry.registerTileEntity(TileDictionary.class, ModInfo.ID + ":tileDictionary");
            ComputerCraftAPI.registerTurtleUpgrade(new UpgradeDictionary());
        }

        if (ConfigHandler.enableAntenna) {
            blockAntenna = new BlockAntenna();
            GameRegistry.registerBlock(blockAntenna, blockAntenna.getUnlocalizedName());

            blockAntennaCell = new BlockAntennaCell();
            GameRegistry.registerBlock(blockAntennaCell, blockAntennaCell.getUnlocalizedName());

            blockAntennaMiniCell = new BlockAntennaMiniCell();
            GameRegistry.registerBlock(blockAntennaMiniCell, blockAntennaMiniCell.getUnlocalizedName());

            blockAntennaController = new BlockAntennaController();
            GameRegistry.registerBlock(blockAntennaController, blockAntennaController.getUnlocalizedName());
            GameRegistry.registerTileEntity(TileAntennaController.class, ModInfo.ID + ":tileAntennaController");
        }
    }

    public final void addRecipes() {
        if (ConfigHandler.enablePlayerDetector) {
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(blockPlayerDetector),
                    "SBS",
                    "BPB",
                    "SCS",

                    'S', Block.stone,
                    'B', Block.stoneButton,
                    'P', Block.pressurePlateStone,
                    'C', ComputerCraftInfo.cc_cable
            ));
        }

        if (ConfigHandler.enableChatBox) {
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(blockChatBox),
                    "GGG",
                    "GNG",
                    "GCG",

                    'G', Item.ingotGold,
                    'N', Block.music,
                    'C', ComputerCraftInfo.cc_cable
            ));
        }

        if (ConfigHandler.enableIronNote) {
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(blockIronNote),
                    "III",
                    "INI",
                    "ICI",

                    'I', Item.ingotIron,
                    'N', Block.music,
                    'C', ComputerCraftInfo.cc_cable
            ));
        }

        if (ConfigHandler.enableKeyboard) {
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(blockKeyboard),
                    "KKK",

                    'K', ModItems.itemKeyboardPart
            ));
        }

        if (ConfigHandler.enableDictionary) {
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(blockDictionary),
                    "SBS",
                    "BDB",
                    "SCS",

                    'S', Block.stone,
                    'B', Item.book,
                    'D', Item.diamond,
                    'C', ComputerCraftInfo.cc_cable
            ));
        }

        if (ConfigHandler.enableAntenna) {
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(blockAntenna, 4),
                    "ICI",
                    "ICI",
                    "ICI",

                    'I', Item.ingotIron,
                    'C', ComputerCraftInfo.cc_cable
            ));
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(blockAntennaCell),
                    "IMI",
                    "MCM",
                    "IMI",

                    'I', Item.ingotIron,
                    'M', ComputerCraftInfo.cc_wirelessModem,
                    'C', ComputerCraftInfo.cc_cable
            ));
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(blockAntennaMiniCell),
                    "IMI",
                    "MCM",
                    "IMI",

                    'I', Item.ingotIron,
                    'M', ModItems.itemMonopoleAntenna,
                    'C', ComputerCraftInfo.cc_cable
            ));
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(blockAntennaController),
                    "ICI",
                    "IWI",
                    "IPI",

                    'I', Item.ingotIron,
                    'C', ComputerCraftInfo.cc_cable,
                    'W', ComputerCraftInfo.cc_wiredModem,
                    'P', ComputerCraftInfo.cc_blockComputer
            ));
        }
    }

}
