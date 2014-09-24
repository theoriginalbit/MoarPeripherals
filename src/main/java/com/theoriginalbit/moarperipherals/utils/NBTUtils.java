/**
 * Copyright (c) 2013-2014, Joshua Asbury (@theoriginalbit)
 * http://wiki.theoriginalbit.com/moarperipherals/
 *
 * MoarPeripherals is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://www.mod-buildcraft.com/MMPL-1.0.txt
 */
package com.theoriginalbit.moarperipherals.utils;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public final class NBTUtils {

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