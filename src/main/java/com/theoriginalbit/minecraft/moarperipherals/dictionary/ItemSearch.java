/**
 * Copyright (c) 2013-2014, Joshua Asbury (@theoriginalbit)
 * http://wiki.theoriginalbit.com/moarperipherals/
 *
 * MoarPeripherals is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://www.mod-buildcraft.com/MMPL-1.0.txt
 */
package com.theoriginalbit.minecraft.moarperipherals.dictionary;

import com.google.common.collect.Lists;
import com.theoriginalbit.minecraft.moarperipherals.common.utils.InventoryUtils;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatAllowedCharacters;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.regex.Pattern;

public final class ItemSearch {

    public static LinkedList<ItemStack> itemMap = Lists.newLinkedList();

    // TODO: figure out a new way to search blocks more reliably

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
