/**
 * Copyright (c) 2013-2014, Joshua Asbury (@theoriginalbit)
 * http://wiki.theoriginalbit.com/moarperipherals/
 *
 * MoarPeripherals is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://www.mod-buildcraft.com/MMPL-1.0.txt
 */
package com.theoriginalbit.moarperipherals.common.registry;

import com.theoriginalbit.moarperipherals.common.config.ConfigHandler;
import com.theoriginalbit.moarperipherals.common.item.abstracts.ItemMoarP;
import com.theoriginalbit.moarperipherals.common.item.ItemSonic;
import com.theoriginalbit.moarperipherals.common.reference.ComputerCraftInfo;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;

/**
 * @author theoriginalbit
 * @since 3/10/2014
 */
public final class ModItems {

    public static final ModItems INSTANCE = new ModItems();

    public static Item itemInkCartridge, itemSonic, itemKeyboardPart, itemMonopoleAntenna;

    private ModItems() {
        // prevent other instances being constructed
    }

    public final void register() {
        if (ConfigHandler.enablePrinter) {
//            itemInkCartridge = new ItemInkCartridge();
//            GameRegistry.registerItem(itemInkCartridge, "itemInkCartridge");
        }

        if (ConfigHandler.isSonicEnabled()) {
            itemSonic = new ItemSonic();
            GameRegistry.registerItem(itemSonic, "itemSonic");
        }

        if (ConfigHandler.enableKeyboard) {
            itemKeyboardPart = new ItemMoarP("keyboardPart");
            GameRegistry.registerItem(itemKeyboardPart, "itemKeyboardPart");
        }

        if (ConfigHandler.enableAntenna) {
            itemMonopoleAntenna = new ItemMoarP("monopolePart");
            GameRegistry.registerItem(itemMonopoleAntenna, "itemMonopoleAntenna");
        }
    }

    public final void addRecipes() {
        if (ConfigHandler.isSonicEnabled()) {
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(itemSonic, 1, 0),
                    "DIG",
                    "IRI",
                    "GIO",

                    'D', "gemDiamond",
                    'I', "ingotIron",
                    'G', "dyeGray",
                    'R', "dustRedstone",
                    'O', Blocks.obsidian
            ));
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(itemSonic, 1, 1),
                    "EIB",
                    "IRI",
                    "BIO",

                    'E', "gemEmerald",
                    'I', "ingotIron",
                    'B', "dyeBrown",
                    'R', "dustRedstone",
                    'O', Blocks.obsidian
            ));
        }

        if (ConfigHandler.enableKeyboard) {
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(itemKeyboardPart),
                    "BBB",
                    "RRR",
                    "SSS",

                    'B', Blocks.stone_button,
                    'R', "dustRedstone",
                    'S', "stone"
            ));
        }

        if (ConfigHandler.enableAntenna) {
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(itemMonopoleAntenna, 4),
                    "M",

                    'M', ComputerCraftInfo.cc_wirelessModem
            ));
        }
    }

}
