/**
 * Copyright (c) 2013-2014, Joshua Asbury (@theoriginalbit)
 * http://wiki.theoriginalbit.com/moarperipherals/
 *
 * MoarPeripherals is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://www.mod-buildcraft.com/MMPL-1.0.txt
 */
package com.theoriginalbit.moarperipherals.common.registry;

import com.theoriginalbit.moarperipherals.common.block.*;
import com.theoriginalbit.moarperipherals.common.block.base.BlockMoarP;
import com.theoriginalbit.moarperipherals.common.itemblock.ItemBlockKeyboard;
import com.theoriginalbit.moarperipherals.reference.ModInfo;
import com.theoriginalbit.moarperipherals.reference.Settings;
import com.theoriginalbit.moarperipherals.common.tile.*;
import com.theoriginalbit.moarperipherals.common.tile.TileAntennaController;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.oredict.OreDictionary;

public final class BlockRegistry {

    public static BlockMoarP blockChatBox;
    public static BlockMoarP blockPlayerDetector;
    public static BlockMoarP blockIronNote;
    public static BlockMoarP blockKeyboard;
    public static BlockMoarP blockPrinter;
    public static BlockMoarP blockDictionary;
    public static BlockMoarP blockAntenna;
    public static BlockMoarP blockAntennaCell;
    public static BlockMoarP blockAntennaModem;
    public static BlockMoarP blockAntennaController;

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
        if (Settings.enableDictionary) {
            blockDictionary = new BlockDictionary();
            registerBlock(blockDictionary, TileDictionary.class, "tileDictionary");
        }
        if (Settings.enableAntenna) {
            blockAntenna = new BlockAntenna();
            registerBlock(blockAntenna);
            blockAntennaCell = new BlockAntennaCell();
            registerBlock(blockAntennaCell);
            blockAntennaModem = new BlockAntennaModem();
            registerBlock(blockAntennaModem);
            blockAntennaController = new BlockAntennaController();
            registerBlock(blockAntennaController, TileAntennaController.class, "tileAntennaController");
        }
    }

    public static void oreRegistration() {
        OreDictionary.registerOre("peripheralChatbox", blockChatBox);
        OreDictionary.registerOre("peripheralPlayerDetector", blockPlayerDetector);
        OreDictionary.registerOre("peripheralIronNote", blockIronNote);
        OreDictionary.registerOre("peripheralKeyboard", blockKeyboard);
    }

    private static void registerBlock(BlockMoarP block) {
        registerBlock(block, null, null, null);
    }

    private static void registerBlock(BlockMoarP block, Class<? extends TileEntity> tile, String teName) {
        registerBlock(block, tile, teName, null);
    }

    private static void registerBlock(BlockMoarP block, Class<? extends TileEntity> tile, String teName, Class<? extends ItemBlock> itemBlock) {
        if (itemBlock != null) {
            GameRegistry.registerBlock(block, itemBlock, block.getUnlocalizedName());
        } else {
            GameRegistry.registerBlock(block, block.getUnlocalizedName());
        }
        if (tile != null) {
            GameRegistry.registerTileEntity(tile, ModInfo.ID + ':' + teName);
        }
    }

}