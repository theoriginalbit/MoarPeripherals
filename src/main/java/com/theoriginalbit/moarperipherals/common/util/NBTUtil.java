/**
 * Copyright 2014-2015 Joshua Asbury (@theoriginalbit)
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.theoriginalbit.moarperipherals.common.util;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public final class NBTUtil {

    public static NBTTagCompound getItemTag(ItemStack stack) {
        initNBTTagCompound(stack);
        return stack.stackTagCompound;
    }

    public static boolean hasTag(ItemStack stack, String key) {
        return stack != null && stack.stackTagCompound != null && stack.stackTagCompound.hasKey(key);
    }

    public static void removeTag(ItemStack stack, String key) {
        if (stack.stackTagCompound != null) {
            stack.stackTagCompound.removeTag(key);
        }
    }

    public static void setString(ItemStack stack, String key, String value) {
        initNBTTagCompound(stack);
        stack.stackTagCompound.setString(key, value);
    }

    public static String getString(ItemStack stack, String key) {
        initNBTTagCompound(stack);
        if (!hasTag(stack, key)) {
            setString(stack, key, "");
        }
        return stack.stackTagCompound.getString(key);
    }

    public static void setInteger(ItemStack stack, String key, int value) {
        initNBTTagCompound(stack);
        stack.stackTagCompound.setInteger(key, value);
    }

    public static int getInteger(ItemStack stack, String key) {
        initNBTTagCompound(stack);
        if (!hasTag(stack, key)) {
            setInteger(stack, key, 0);
        }
        return stack.stackTagCompound.getInteger(key);
    }

    private static void initNBTTagCompound(ItemStack stack) {
        if (stack.stackTagCompound == null) {
            stack.setTagCompound(new NBTTagCompound());
        }
    }

}