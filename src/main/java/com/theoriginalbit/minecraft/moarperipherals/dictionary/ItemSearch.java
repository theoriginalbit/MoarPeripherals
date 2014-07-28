package com.theoriginalbit.minecraft.moarperipherals.dictionary;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import dan200.computercraft.api.peripheral.IComputerAccess;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * A Minecraft mod that adds more peripherals into the ComputerCraft mod.
 * Official Thread:
 * http://www.computercraft.info/forums2/index.php?/topic/19357-
 * Official Wiki:
 * http://wiki.theoriginalbit.com/moarperipherals/
 * <p/>
 * Copyright (C) 2014  Joshua Asbury (@theoriginalbit)
 * <p/>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p/>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p/>
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/.
 */
public class ItemSearch {

    public static LinkedList<ItemStack> itemMap = Lists.newLinkedList();

    private static final LinkedHashMap<String, IComputerAccess> searchQueue = Maps.newLinkedHashMap();

    private static final RestartableTask itemLoadTask = new RestartableTask("MoarPeripherals Item Loading") {

        @Override
        public void execute() {
            for (Item item : Item.itemsList) {
                ItemSearch.itemMap.add(new ItemStack(item));
            }
        }

    };

    private static final RestartableTask searchTask = new RestartableTask("MoarPeripherals Item Search") {
        @Override
        public void execute() {
            for (Map.Entry<String, IComputerAccess> search : searchQueue.entrySet()) {
                final String term = search.getKey();
                final LinkedList<ItemStack> result = Lists.newLinkedList();
                final PatternItemMatcher matcher = new PatternItemMatcher(Pattern.compile(term));
                for (ItemStack stack : itemMap) {
                    if (matcher.matches(stack)) {
                        result.addLast(stack);
                    }
                }
                final IComputerAccess computer = search.getValue();
                computer.queueEvent("search_complete", new Object[]{search.hashCode()});
                searchQueue.remove(term);
            }
        }
    };

    public static void init() {
        itemLoadTask.restart();
    }

    public static int search(String term, IComputerAccess computer) {
        searchQueue.put(term, computer);
        if (!searchTask.running()) {
            searchTask.restart();
        }
        return searchQueue.get(term).hashCode();
    }

    public static Recipe recipe(ItemStack stack) {
        return null;
    }

    public static ArrayList<Recipe> usage(ItemStack stack) {
        return null;
    }

    public final class Recipe {
    }
}
