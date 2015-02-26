/**
 * Copyright 2014 Joshua Asbury (@theoriginalbit)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.theoriginalbit.moarperipherals.common.registry;

import com.theoriginalbit.moarperipherals.common.block.*;
import com.theoriginalbit.moarperipherals.common.config.ConfigHandler;
import com.theoriginalbit.moarperipherals.common.item.block.ItemBlockPaired;
import com.theoriginalbit.moarperipherals.common.tile.*;
import com.theoriginalbit.moarperipherals.common.reference.ComputerCraftInfo;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;

/**
 * @author theoriginalbit
 * @since 3/10/2014.
 */
public final class ModBlocks {

    public static final ModBlocks INSTANCE = new ModBlocks();

    private ModBlocks() {
        // prevent other instances being constructed
    }

    public static Block blockChatBox, blockChatBoxAdmin, blockPlayerDetector, blockIronNote, blockKeyboardMac, blockKeyboardPc,
            blockPrinter, blockAntenna, blockAntennaCell, blockAntennaMiniCell, blockAntennaController, blockTurtleTeleport,
            blockMiniAntenna, blockFireworks, blockFireworksCreative, blockComputerCrafter;

    public final void register() {
        if (ConfigHandler.enablePlayerDetector) {
            blockPlayerDetector = new BlockPlayerDetector();
            GameRegistry.registerBlock(blockPlayerDetector, "blockPlayerDetector");
            GameRegistry.registerTileEntity(TilePlayerDetector.class, "tilePlayerDetector");

            // add the player detector to the ore dictionary as per request from tattyseal
            OreDictionary.registerOre("peripheralPlayerDetector", blockPlayerDetector);
        }

        if (ConfigHandler.enableChatBox) {
            // normal ChatBox
            blockChatBox = new BlockChatBox();
            GameRegistry.registerBlock(blockChatBox, "blockChatBox");
            GameRegistry.registerTileEntity(TileChatBox.class, "tileChatBox");
            // admin (creative) ChatBox
            blockChatBoxAdmin = new BlockChatBoxAdmin();
            GameRegistry.registerBlock(blockChatBoxAdmin, "blockChatBoxAdmin");
            GameRegistry.registerTileEntity(TileChatBoxAdmin.class, "tileChatBoxAdmin");
        }

        if (ConfigHandler.enableIronNote) {
            blockIronNote = new BlockIronNote();
            GameRegistry.registerBlock(blockIronNote, "blockIronNote");
            GameRegistry.registerTileEntity(TileIronNote.class, "tileIronNote");
        }

        if (ConfigHandler.enableKeyboard) {
            blockKeyboardMac = new BlockKeyboardMac();
            blockKeyboardPc = new BlockKeyboardPc();
            GameRegistry.registerBlock(blockKeyboardMac, ItemBlockPaired.class, "blockKeyboardMac");
            GameRegistry.registerBlock(blockKeyboardPc, ItemBlockPaired.class, "blockKeyboardPc");
            GameRegistry.registerTileEntity(TileKeyboard.class, "tileKeyboard");
        }

        if (ConfigHandler.enablePrinter) {
            blockPrinter = new BlockPrinter();
            GameRegistry.registerBlock(blockPrinter, "blockPrinter");
            GameRegistry.registerTileEntity(TilePrinter.class, "tilePrinter");
        }

        if (ConfigHandler.enableAntenna) {
            blockAntenna = new BlockAntenna();
            GameRegistry.registerBlock(blockAntenna, "blockAntenna");

            blockAntennaCell = new BlockAntennaCell();
            GameRegistry.registerBlock(blockAntennaCell, "blockAntennaCell");

            blockAntennaMiniCell = new BlockAntennaMiniCell();
            GameRegistry.registerBlock(blockAntennaMiniCell, "blockAntennaMiniCell");

            blockAntennaController = new BlockAntennaController();
            GameRegistry.registerBlock(blockAntennaController, "blockAntennaController");
            GameRegistry.registerTileEntity(TileAntennaController.class, "tileAntennaController");
        }

        if (ConfigHandler.enableTurtleTeleport) {
            blockTurtleTeleport = new BlockTurtleTeleport();
            GameRegistry.registerBlock(blockTurtleTeleport, "blockTurtleTeleport");
            GameRegistry.registerTileEntity(TileTurtleTeleport.class, "tileTurtleTeleport");
        }

        if (ConfigHandler.enableFireworkLauncher) {
            // standard firework launcher
            blockFireworks = new BlockFireworks();
            GameRegistry.registerBlock(blockFireworks, "blockFireworks");
            GameRegistry.registerTileEntity(TileFireworks.class, "tileFireworks");
            // creative firework launcher
            blockFireworksCreative = new BlockFireworksCreative();
            GameRegistry.registerBlock(blockFireworksCreative, "blockFireworksCreative");
            GameRegistry.registerTileEntity(TileFireworksCreative.class, "tileFireworksCreative");
        }

        if (ConfigHandler.enableMiniAntenna) {
            blockMiniAntenna = new BlockMiniAntenna();
            GameRegistry.registerBlock(blockMiniAntenna, "blockMiniAntenna");
            GameRegistry.registerTileEntity(TileMiniAntenna.class, "tileMiniAntenna");
        }

        if (ConfigHandler.enableComputerCrafter) {
            blockComputerCrafter = new BlockComputerCrafter();
            GameRegistry.registerBlock(blockComputerCrafter, "blockComputerCrafter");
            GameRegistry.registerTileEntity(TileComputerCrafter.class, "tileComputerCrafter");
        }
    }

    public final void addRecipes() {
        if (ConfigHandler.enablePlayerDetector) {
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(blockPlayerDetector),
                    "SBS",
                    "BPB",
                    "SCS",

                    'S', "stone",
                    'B', Blocks.stone_button,
                    'P', Blocks.stone_pressure_plate,
                    'C', ComputerCraftInfo.cc_cable
            ));
        }

        if (ConfigHandler.enableChatBox) {
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(blockChatBox),
                    "GGG",
                    "GNG",
                    "GCG",

                    'G', "ingotGold",
                    'N', Blocks.noteblock,
                    'C', ComputerCraftInfo.cc_cable
            ));
        }

        if (ConfigHandler.enableIronNote) {
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(blockIronNote),
                    "III",
                    "INI",
                    "ICI",

                    'I', "ingotIron",
                    'N', Blocks.noteblock,
                    'C', ComputerCraftInfo.cc_cable
            ));
        }

        if (ConfigHandler.enableKeyboard) {
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(blockKeyboardMac),
                    "KKK",
                    " A ",

                    'K', ModItems.itemKeyboardPart,
                    'A', Items.apple
            ));
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(blockKeyboardPc),
                    "KKK",
                    " W ",

                    'K', ModItems.itemKeyboardPart,
                    'W', Blocks.glass_pane
            ));
        }

        if (ConfigHandler.enablePrinter) {
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(blockPrinter),
                    "GGG",
                    "GRG",
                    "GIG",

                    'G', "ingotGold",
                    'R', "dustRedstone",
                    'I', new ItemStack(ModItems.itemInkCartridge, 1, 4)
            ));
        }

        if (ConfigHandler.enableAntenna) {
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(blockAntenna, 4),
                    "ICI",
                    "ICI",
                    "ICI",

                    'I', "ingotIron",
                    'C', ComputerCraftInfo.cc_cable
            ));
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(blockAntennaCell),
                    "IMI",
                    "MCM",
                    "IMI",

                    'I', "ingotIron",
                    'M', ComputerCraftInfo.cc_wirelessModem,
                    'C', ComputerCraftInfo.cc_cable
            ));
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(blockAntennaMiniCell),
                    "IMI",
                    "MCM",
                    "IMI",

                    'I', "ingotIron",
                    'M', ModItems.itemMonopoleAntenna,
                    'C', ComputerCraftInfo.cc_cable
            ));
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(blockAntennaController),
                    "ICI",
                    "IWI",
                    "IPI",

                    'I', "ingotIron",
                    'C', ComputerCraftInfo.cc_cable,
                    'W', ComputerCraftInfo.cc_wiredModem,
                    'P', ComputerCraftInfo.cc_blockComputer
            ));
        }

        if (ConfigHandler.enableTurtleTeleport) {
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(blockTurtleTeleport),
                    "IEI",
                    "EOE",
                    "ICI",

                    'I', "ingotIron",
                    'E', Items.ender_pearl,
                    'O', Blocks.obsidian,
                    'C', ComputerCraftInfo.cc_cable
            ));
        }

        if (ConfigHandler.enableFireworkLauncher) {
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(blockFireworks),
                    "DDD",
                    "WFW",
                    "ICI",

                    'D', Blocks.dispenser,
                    'W', Blocks.chest,
                    'F', Items.flint_and_steel,
                    'I', "ingotIron",
                    'C', ComputerCraftInfo.cc_cable
            ));
        }

        if (ConfigHandler.enableMiniAntenna) {
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(blockMiniAntenna),
                    "MCM",
                    "MCM",
                    "IWI",

                    'M', ModItems.itemMonopoleAntenna,
                    'C', ComputerCraftInfo.cc_cable,
                    'I', "ingotIron",
                    'W', ComputerCraftInfo.cc_wiredModem
            ));
        }

        if (ConfigHandler.enableComputerCrafter) {
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(blockComputerCrafter),
                    "ITI",
                    "IWI",
                    "ICI",

                    'I', "ingotIron",
                    'T', Blocks.crafting_table,
                    'W', Blocks.chest,
                    'C', ComputerCraftInfo.cc_cable
            ));
        }
    }

}
