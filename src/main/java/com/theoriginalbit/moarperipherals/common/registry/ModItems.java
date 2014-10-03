package com.theoriginalbit.moarperipherals.common.registry;

import com.theoriginalbit.moarperipherals.common.config.ConfigHandler;
import com.theoriginalbit.moarperipherals.common.item.abstracts.ItemMoarP;
import com.theoriginalbit.moarperipherals.common.item.ItemSonic;
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
            itemKeyboardPart = new ItemMoarP("keyboardPart");
            GameRegistry.registerItem(itemKeyboardPart, itemKeyboardPart.getUnlocalizedName());
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
    }

}
