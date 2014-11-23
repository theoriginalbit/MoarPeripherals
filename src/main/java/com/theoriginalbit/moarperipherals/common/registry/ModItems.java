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

import com.theoriginalbit.moarperipherals.common.config.ConfigHandler;
import com.theoriginalbit.moarperipherals.common.item.ItemInkCartridge;
import com.theoriginalbit.moarperipherals.common.item.abstracts.ItemMoarP;
import com.theoriginalbit.moarperipherals.common.item.ItemSonic;
import com.theoriginalbit.moarperipherals.common.reference.ComputerCraftInfo;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;

/**
 * @author theoriginalbit
 * @since 3/10/2014
 */
public final class ModItems {

    public static final ModItems INSTANCE = new ModItems();

    // custom items
    public static Item itemInkCartridge, itemSonic, itemMonopoleAntenna;

    // materials, don't need custom implementations
    public static Item itemKeyboardPart, itemUpgradeSolar, itemUpgradeOreScanner;

    private ModItems() {
        // prevent other instances being constructed
    }

    public final void register() {
        if (ConfigHandler.enablePrinter) {
            itemInkCartridge = new ItemInkCartridge();
            GameRegistry.registerItem(itemInkCartridge, "itemInkCartridge");
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

        if (ConfigHandler.enableUpgradeSolar) {
            itemUpgradeSolar = new ItemMoarP("solarPanel");
            GameRegistry.registerItem(itemUpgradeSolar, "itemSolarPanel");
        }

        if (ConfigHandler.enableUpgradeOreScanner) {
            itemUpgradeOreScanner = new ItemMoarP("oreScanner");
            GameRegistry.registerItem(itemUpgradeOreScanner, "itemOreScanner");
        }
    }

    public final void addRecipes() {
        if (ConfigHandler.enablePrinter) {
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(itemInkCartridge, 1, 4),
                    "SSS",
                    "SBS",
                    "SRS",

                    'S', "stone",
                    'B', Items.bucket,
                    'R', "dustRedstone"
            ));
            // TODO: make the Fake recipe stuff for loading ink cartridges
        }

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

        if (ConfigHandler.enableUpgradeSolar) {
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(itemUpgradeSolar),
                    "SSS",
                    "ICI",

                    'S', Blocks.daylight_detector,
                    'I', "ingotIron",
                    'C', ComputerCraftInfo.cc_cable
            ));
        }

        if (ConfigHandler.enableUpgradeOreScanner) {
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(itemUpgradeOreScanner),
                    "III",
                    "DGS",
                    "ECE",

                    'I', "ingotIron",
                    'D', new ItemStack(Blocks.dirt, 1, 0),
                    'G', Blocks.gravel,
                    'S', "stone",
                    'E', Items.ender_eye,
                    'C', ComputerCraftInfo.cc_cable
            ));
        }
    }

}
