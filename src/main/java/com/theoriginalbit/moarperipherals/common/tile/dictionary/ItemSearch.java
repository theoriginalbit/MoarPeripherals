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
package com.theoriginalbit.moarperipherals.common.tile.dictionary;

import com.google.common.collect.Lists;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.regex.Pattern;

/**
 * @author theoriginalbit
 * @since 14/10/2014
 */
public class ItemSearch {

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
        return pattern.matcher(stack.getDisplayName().toLowerCase()).find();
    }

}
