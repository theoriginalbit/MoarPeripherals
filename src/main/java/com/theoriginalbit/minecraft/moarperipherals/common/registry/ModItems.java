/**
 * Copyright (c) 2013-2014, Joshua Asbury (@theoriginalbit)
 * http://wiki.theoriginalbit.com/moarperipherals/
 *
 * MoarPeripherals is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://www.mod-buildcraft.com/MMPL-1.0.txt
 */
package com.theoriginalbit.minecraft.moarperipherals.common.registry;

import com.theoriginalbit.minecraft.moarperipherals.common.config.ConfigHandler;
import com.theoriginalbit.minecraft.moarperipherals.common.item.ItemSonic;
import com.theoriginalbit.minecraft.moarperipherals.common.item.abstracts.ItemMoarP;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;

/**
 * @author theoriginalbit
 * @since 4/10/2014
 */
public class ModItems {

    public static final ModItems INSTANCE = new ModItems();

    public static Item itemInkCartridge, itemSonic, itemKeyboardPart;

    private ModItems() {
        // prevent other instances being constructed
    }

    public final void register() {
        if (ConfigHandler.enablePrinter) {
//            itemInkCartridge = new ItemInkCartridge();
//            GameRegistry.registerItem(itemInkCartridge, itemInkCartridge.getUnlocalizedName());
        }

        if (ConfigHandler.isSonicEnabled()) {
            itemSonic = new ItemSonic();
            GameRegistry.registerItem(itemSonic, itemSonic.getUnlocalizedName());
        }

        if (ConfigHandler.enableKeyboard) {
            itemKeyboardPart = new ItemMoarP(ConfigHandler.itemIdKeyboardPart, "keyboardPart");
            GameRegistry.registerItem(itemKeyboardPart, itemKeyboardPart.getUnlocalizedName());
        }
    }

    public final void addRecipes() {
        if (ConfigHandler.isSonicEnabled()) {
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(itemSonic, 1, 0),
                    "DIG",
                    "IRI",
                    "GIO",

                    'D', Item.diamond,
                    'I', Item.ingotIron,
                    'G', "dyeGray",
                    'R', Item.redstone,
                    'O', Block.obsidian
            ));
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(itemSonic, 1, 1),
                    "EIB",
                    "IRI",
                    "BIO",

                    'E', Item.emerald,
                    'I', Item.ingotIron,
                    'B', "dyeBrown",
                    'R', Item.redstone,
                    'O', Block.obsidian
            ));
        }

        if (ConfigHandler.enableKeyboard) {
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(itemKeyboardPart),
                    "BBB",
                    "RRR",
                    "SSS",

                    'B', Block.stoneButton,
                    'R', Item.redstone,
                    'S', Block.stone
            ));
        }
    }

}
