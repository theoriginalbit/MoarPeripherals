/**
 * Copyright (c) 2013-2014, Joshua Asbury (@theoriginalbit)
 * http://wiki.theoriginalbit.com/moarperipherals/
 *
 * MoarPeripherals is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://www.mod-buildcraft.com/MMPL-1.0.txt
 */
package com.theoriginalbit.moarperipherals.common.registry;

import com.theoriginalbit.moarperipherals.reference.ComputerCraftInfo;
import com.theoriginalbit.moarperipherals.reference.Settings;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;

public final class RecipeRegistry {

    public static void init() {
        if (Settings.enablePlayerDetector) {
            GameRegistry.addRecipe(new ItemStack(BlockRegistry.blockPlayerDetector), "SBS", "BPB", "SCS", 'S', Blocks.stone, 'B', Blocks.stone_button, 'P', Blocks.stone_pressure_plate, 'C', ComputerCraftInfo.cc_cable);
        }
        if (Settings.enableChatBox) {
            GameRegistry.addRecipe(new ItemStack(BlockRegistry.blockChatBox), "GGG", "GNG", "GCG", 'G', Items.gold_ingot, 'N', Blocks.noteblock, 'C', ComputerCraftInfo.cc_cable);
        }
        if (Settings.enableIronNote) {
            GameRegistry.addRecipe(new ItemStack(BlockRegistry.blockIronNote), "III", "INI", "ICI", 'I', Items.iron_ingot, 'N', Blocks.noteblock, 'C', ComputerCraftInfo.cc_cable);
        }
        if (Settings.enableKeyboard) {
            GameRegistry.addRecipe(new ItemStack(MaterialRegistry.materialKeyboardPart), "BBB", "RRR", "SSS", 'B', Blocks.stone_button, 'R', Items.redstone, 'S', Blocks.stone);
            GameRegistry.addRecipe(new ItemStack(BlockRegistry.blockKeyboard), "KKK", 'K', MaterialRegistry.materialKeyboardPart);
        }
        // TODO: if Settings.enablePrinter
        if (Settings.enableDictionary) {
            GameRegistry.addRecipe(new ItemStack(BlockRegistry.blockDictionary), "SBS", "BDB", "SCS", 'S', Blocks.stone, 'B', Items.book, 'D', Items.diamond, 'C', ComputerCraftInfo.cc_cable);
        }
        if (Settings.isSonicEnabled()) {
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemRegistry.itemSonic, 1, 0), "DIG", "IRI", "GIO", 'D', Items.diamond, 'I', Items.iron_ingot, 'G', "dyeGray", 'R', Items.redstone, 'O', Blocks.obsidian));
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemRegistry.itemSonic, 1, 1), "EIB", "IRI", "BIO", 'E', Items.emerald, 'I', Items.iron_ingot, 'B', "dyeBrown", 'R', Items.redstone, 'O', Blocks.obsidian));
        }
        if (Settings.enableAntenna) {
            GameRegistry.addRecipe(new ItemStack(BlockRegistry.blockAntenna), "ICI", "ICI", "ICI", 'I', Items.iron_ingot, 'C', ComputerCraftInfo.cc_cable);
            GameRegistry.addRecipe(new ItemStack(BlockRegistry.blockAntennaCell), "IMI", "MCM", "IMI", 'I', Items.iron_ingot, 'M', ComputerCraftInfo.cc_wirelessModem, 'C', ComputerCraftInfo.cc_cable);
            GameRegistry.addRecipe(new ItemStack(BlockRegistry.blockAntennaModem), "ICI", "CCC", "ICI", 'I', Items.iron_ingot, 'C', ComputerCraftInfo.cc_cable);
            GameRegistry.addRecipe(new ItemStack(BlockRegistry.blockAntennaController), "ICI", "DWD", "IPI", 'I', Items.iron_ingot, 'C', ComputerCraftInfo.cc_cable, 'D', Items.diamond, 'W', ComputerCraftInfo.cc_wiredModem, 'P', ComputerCraftInfo.cc_blockComputer);
        }
    }

}
