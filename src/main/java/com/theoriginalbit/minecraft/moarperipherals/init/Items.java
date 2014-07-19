package com.theoriginalbit.minecraft.moarperipherals.init;

import com.theoriginalbit.minecraft.moarperipherals.item.ItemGeneric;
import com.theoriginalbit.minecraft.moarperipherals.item.ItemInkCartridge;
import com.theoriginalbit.minecraft.moarperipherals.reference.Settings;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class Items {

    public static ItemGeneric itemInkCartridge;
    public static ItemGeneric itemKeyboardPart;

    public static void init() {
        if (Settings.enablePrinter) {
            itemInkCartridge = new ItemInkCartridge();
            GameRegistry.registerItem(itemInkCartridge, itemInkCartridge.getUnlocalizedName());
        }
        if (Settings.enableKeyboard) {
            itemKeyboardPart = new ItemGeneric(Settings.itemKeyboardPart, "keyboardPart");
            GameRegistry.registerItem(itemKeyboardPart, itemKeyboardPart.getUnlocalizedName());
        }
    }

    public static void initRecipes() {
        if (Settings.enablePrinter) {

        }
        if (Settings.enableKeyboard) {
            GameRegistry.addRecipe(new ItemStack(itemKeyboardPart), "BBB", "RRR", "SSS", 'B', Block.stoneButton, 'R', Item.redstone, 'S', Block.stone);
        }
    }

    public static void oreRegistration() {

    }

}