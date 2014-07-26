package com.theoriginalbit.minecraft.moarperipherals.handler;

import com.theoriginalbit.minecraft.moarperipherals.init.Blocks;
import com.theoriginalbit.minecraft.moarperipherals.init.Items;
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
public final class RecipeHandler {

    public static void init() {
        if (Settings.enablePlayerDetector) {
            GameRegistry.addRecipe(new ItemStack(Blocks.blockPlayerDetector), "SBS", "BRB", "SBS", 'S', Block.stone, 'B', Block.stoneButton, 'R', Item.redstone);
        }
        if (Settings.enableChatBox) {
            GameRegistry.addRecipe(new ItemStack(Blocks.blockChatBox), "GGG", "GNG", "GRG", 'G', Item.ingotGold, 'N', Block.music, 'R', Item.redstone);
        }
        if (Settings.enableIronNote) {
            GameRegistry.addRecipe(new ItemStack(Blocks.blockIronNote), "III", "INI", "IRI", 'I', Item.ingotIron, 'N', Block.music, 'R', Item.redstone);
        }
        if (Settings.enableKeyboard) {
            GameRegistry.addRecipe(new ItemStack(Items.itemKeyboardPart), "BBB", "RRR", "SSS", 'B', Block.stoneButton, 'R', Item.redstone, 'S', Block.stone);
            GameRegistry.addRecipe(new ItemStack(Blocks.blockKeyboard), "KKK", 'K', Items.itemKeyboardPart);
        }
        if (Settings.enablePrinter) {

        }
    }

}
