package com.theoriginalbit.minecraft.moarperipherals.registry;

import com.theoriginalbit.minecraft.moarperipherals.reference.ComputerCraftInfo;
import com.theoriginalbit.minecraft.moarperipherals.reference.Settings;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

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
public final class RecipeRegistry {

    public static void init() {
        if (Settings.enablePlayerDetector) {
            GameRegistry.addRecipe(new ItemStack(BlockRegistry.blockPlayerDetector), "SBS", "BPB", "SCS", 'S', Block.stone, 'B', Block.stoneButton, 'P', Block.pressurePlateStone, 'C', ComputerCraftInfo.cc_cable);
        }
        if (Settings.enableChatBox) {
            GameRegistry.addRecipe(new ItemStack(BlockRegistry.blockChatBox), "GGG", "GNG", "GCG", 'G', Item.ingotGold, 'N', Block.music, 'C', ComputerCraftInfo.cc_cable);
        }
        if (Settings.enableIronNote) {
            GameRegistry.addRecipe(new ItemStack(BlockRegistry.blockIronNote), "III", "INI", "ICI", 'I', Item.ingotIron, 'N', Block.music, 'C', ComputerCraftInfo.cc_cable);
        }
        if (Settings.enableKeyboard) {
            GameRegistry.addRecipe(new ItemStack(MaterialRegistry.materialKeyboardPart), "BBB", "RRR", "SSS", 'B', Block.stoneButton, 'R', Item.redstone, 'S', Block.stone);
            GameRegistry.addRecipe(new ItemStack(BlockRegistry.blockKeyboard), "KKK", 'K', MaterialRegistry.materialKeyboardPart);
        }
        if (Settings.enablePrinter) {

        }
        if (Settings.enableDictionary) {
            GameRegistry.addRecipe(new ItemStack(BlockRegistry.blockDictionary), "SBS", "BDB", "SCS", 'S', Block.stone, 'B', Item.book, 'D', Item.diamond, 'C', ComputerCraftInfo.cc_cable);
        }
        if (Settings.isSonicEnabled()) {
            GameRegistry.addRecipe(new ItemStack(ItemRegistry.itemSonic), "DIG", "IRI", "GIO", 'D', Item.diamond, 'I', Item.ingotIron, 'G', new ItemStack(Item.dyePowder, 1, 8), 'R', Item.redstone, 'O', Block.obsidian);
        }
        if (Settings.enableAntenna) {
            GameRegistry.addRecipe(new ItemStack(BlockRegistry.blockAntenna), "ICI", "ICI", "ICI", 'I', Item.ingotIron, 'C', ComputerCraftInfo.cc_cable);
            GameRegistry.addRecipe(new ItemStack(BlockRegistry.blockAntennaCell), "IMI", "MCM", "IMI", 'I', Item.ingotIron, 'M', ComputerCraftInfo.cc_wirelessModem, 'C', ComputerCraftInfo.cc_cable);
            GameRegistry.addRecipe(new ItemStack(BlockRegistry.blockAntennaModem), "ICI", "CCC", "ICI", 'I', Item.ingotIron, 'C', ComputerCraftInfo.cc_cable);
            GameRegistry.addRecipe(new ItemStack(BlockRegistry.blockAntennaController), "ICI", "DWD", "IPI", 'I', Item.ingotIron, 'C', ComputerCraftInfo.cc_cable, 'D', Item.diamond, 'W', ComputerCraftInfo.cc_wiredModem, 'P', ComputerCraftInfo.cc_blockComputer);
        }
    }

}
