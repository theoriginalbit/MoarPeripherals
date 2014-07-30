package com.theoriginalbit.minecraft.moarperipherals.dictionary;

import com.google.common.collect.Lists;
import com.theoriginalbit.minecraft.moarperipherals.utils.InventoryUtils;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatAllowedCharacters;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.regex.Pattern;

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
public final class ItemSearch {

    public static LinkedList<ItemStack> itemMap = Lists.newLinkedList();

    public static void init() {
        for (Item item : Item.itemsList) {
            if (item != null) {
                itemMap.add(new ItemStack(item));
            }
        }
    }

    public static ArrayList<ItemStack> search(String term) throws Exception {
        ArrayList<ItemStack> results = Lists.newArrayList();

        Pattern pattern;
        try {
             pattern = Pattern.compile(term.toLowerCase().replace(".", "").replace("?", ".").replace("*", ".+?"));
        } catch (Exception e) {
            throw new Exception("Invalid search term");
        }

        for (ItemStack stack : itemMap) {
            if (matches(stack, pattern)) {
                results.add(stack);
            }
        }

        return results;
    }

    private static boolean matches(ItemStack stack, Pattern pattern) {
        return pattern.matcher(sanitise(InventoryUtils.getItemName(stack).toLowerCase())).find();
    }

    private static String sanitise(String s) {
        return ChatAllowedCharacters.filerAllowedCharacters(s.replaceAll("§.", ""));
    }

}
