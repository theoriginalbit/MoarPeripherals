package com.theoriginalbit.minecraft.moarperipherals.registry;

import com.theoriginalbit.minecraft.moarperipherals.block.*;
import com.theoriginalbit.minecraft.moarperipherals.block.base.BlockMPBase;
import com.theoriginalbit.minecraft.moarperipherals.itemblock.ItemBlockKeyboard;
import com.theoriginalbit.minecraft.moarperipherals.reference.ModInfo;
import com.theoriginalbit.minecraft.moarperipherals.reference.Settings;
import com.theoriginalbit.minecraft.moarperipherals.tile.*;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.oredict.OreDictionary;

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
public final class BlockRegistry {

    public static BlockMPBase blockChatBox;
    public static BlockMPBase blockPlayerDetector;
    public static BlockMPBase blockIronNote;
    public static BlockMPBase blockKeyboard;
    public static BlockMPBase blockPrinter;
    public static BlockMPBase blockDictionary;

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
    }

    public static void oreRegistration() {
        OreDictionary.registerOre("peripheralChatbox", blockChatBox);
        OreDictionary.registerOre("peripheralPlayerDetector", blockPlayerDetector);
        OreDictionary.registerOre("peripheralIronNote", blockIronNote);
        OreDictionary.registerOre("peripheralKeyboard", blockKeyboard);
    }

    private static void registerBlock(BlockMPBase block, Class<? extends TileEntity> tile, String teName) {
        registerBlock(block, tile, teName, null);
    }

    private static void registerBlock(BlockMPBase block, Class<? extends TileEntity> tile, String teName, Class<? extends ItemBlock> itemBlock) {
        if (itemBlock != null) {
            GameRegistry.registerBlock(block, itemBlock, block.getUnlocalizedName());
        } else {
            GameRegistry.registerBlock(block, block.getUnlocalizedName());
        }
        GameRegistry.registerTileEntity(tile, ModInfo.ID + ':' + teName);
    }

}